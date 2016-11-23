package io.j1st.controller;

import io.j1st.controller.entity.MqttUpstreamEntity;
import io.j1st.controller.mqtt.MqttConnThread;
import io.j1st.controller.entity.Registry;
import io.j1st.controller.quartz.UpstreamJob;
import io.j1st.storage.MongoStorage;
import io.j1st.storage.entity.Agent;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bson.types.ObjectId;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Agent Emulator
 * <p>
 * Simple Test Not professional
 */
public class AgentEmulator {
    private static final Logger logger = LoggerFactory.getLogger(AgentEmulator.class);

    public static int a = 1;

    public static void main(String[] args) throws Exception {
        logger.debug("Starting agent emulator module ...");

        // load config
        logger.debug("Loading product id by config files ...");

        // PropertiesConfiguration smsConfig;
        PropertiesConfiguration productIdConfig;
        PropertiesConfiguration mongoConfig;
        PropertiesConfiguration mqttConfig;
        PropertiesConfiguration quartzConfig;

        if (args.length >= 4) {
            productIdConfig = new PropertiesConfiguration(args[0]);
            mongoConfig = new PropertiesConfiguration(args[1]);
            mqttConfig = new PropertiesConfiguration(args[2]);
            quartzConfig = new PropertiesConfiguration(args[3]);

        } else {
            productIdConfig = new PropertiesConfiguration("config/product.properties");
            mongoConfig = new PropertiesConfiguration("config/mongo.properties");
            mqttConfig = new PropertiesConfiguration("config/mqtt.properties");
            quartzConfig = new PropertiesConfiguration("config/quartz.properties");

        }

        //mongodb
        MongoStorage mogo = new MongoStorage();
        mogo.init(mongoConfig);
        MqttUpstreamEntity mqttUpstreamEntity = new MqttUpstreamEntity(mogo);
        //  List<Agent> agents = mogo.getAgentsByProductId(new ObjectId(productIdConfig.getString("product_id")));
        List<Agent> agents = mogo.getAgentsByProductId(
                new ObjectId(productIdConfig.getString("batP_product_id")),
                new ObjectId(productIdConfig.getString("pVPower_product_id")),
                new ObjectId(productIdConfig.getString("load_product_id")),
                new ObjectId(productIdConfig.getString("grid_product_id")),
                new ObjectId(productIdConfig.getString("car_product_id")));
        // Mqtt
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient mqtt;
        MqttConnectOptions options;
        //Agent agent= agents.get(0);
        for (Agent agent : agents) {
            System.out.println("agent.getId()" + agent.getId());
            System.out.println(agent.getProductId());
            mqtt = new MqttClient(mqttConfig.getString("mqtt.url"), agent.getId().toHexString(), persistence);
            String agentId = agent.getId().toHexString();
            String token = agent.getToken();
            options = new MqttConnectOptions();
            options.setUserName(agentId);
            options.setPassword(token.toCharArray());
            MqttConnThread mqttConnThread = new MqttConnThread(mqtt, options);
            //添加新线程到线程池
            Registry.INSTANCE.startThread(mqttConnThread);
            String type = null;
                if(agent.getProductId().toString().equals(productIdConfig.getString("batP_product_id")))
                {
                    type = "batP";
                }
                else if(agent.getProductId().toString().equals(productIdConfig.getString("load_product_id")))
                {
                    type = "load";
                }
                else if(agent.getProductId().toString().equals(productIdConfig.getString("grid_product_id")))
                {
                    type = "grid";
                }
                else if(agent.getProductId().toString().equals(productIdConfig.getString("car_product_id")))
                {
                    type = "car";
                }
                else if(agent.getProductId().toString().equals(productIdConfig.getString("pVPower_product_id")))
                {
                    type = "pVP";
                }
            //保存mqtt连接信息
            Registry.INSTANCE.saveSession(agent.getId().toHexString(), mqttConnThread);
            Registry.INSTANCE.saveType(agent.getId().toHexString(), type);
            //if(a++==4000) break;
        }

        // quartz任务
        Properties pros = new Properties();
        pros.setProperty("org.quartz.threadPool.threadCount", quartzConfig.getString("org.quartz.threadPool.threadCount"));
        Scheduler scheduler = new StdSchedulerFactory(pros).getScheduler();
        Trigger trigger = newTrigger()
                .withIdentity("j1st_trigger", "J1st_trigger")
                .withSchedule(cronSchedule(quartzConfig.getString("scheduler.cron")))
                .build();
        JobDetail job = newJob(UpstreamJob.class)
                .withIdentity("j1st_job", "J1st_job")
                .usingJobData("AgentId", "j1st")
                .build();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        logger.info("Agent emulator module is up and running.");
    }
}
