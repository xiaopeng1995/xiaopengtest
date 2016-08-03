package io.j1st.controller;

import io.j1st.controller.mqtt.MqttConnThread;
import io.j1st.controller.mqtt.Registry;
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

        System.out.println("肖鹏你好");
        logger.info("Agent emulator module is up and running.");
    }
}
