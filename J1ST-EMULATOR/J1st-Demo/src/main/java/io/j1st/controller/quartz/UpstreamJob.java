package io.j1st.controller.quartz;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.j1st.controller.entity.MqttUpstreamEntity;
import io.j1st.controller.mqtt.MqttConnThread;
import io.j1st.controller.mqtt.Registry;
import io.j1st.controller.util.JsonUtils;
import io.j1st.storage.entity.Stream;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Upstream Job
 */
public class UpstreamJob implements Job {
    Logger logger= LoggerFactory.getLogger(UpstreamJob.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // mqtt topic
        String topic;
        Set<String> agentIds = Registry.INSTANCE.getSession().keySet();
        MqttConnThread mqttConnThread;
        for (String agentId : agentIds) {
            logger.debug("agent:{} 上发数据",agentId);
            mqttConnThread=Registry.INSTANCE.getSession().get(agentId);
            topic = getTopic(agentId);
            if (mqttConnThread!=null && mqttConnThread.getMqttClient().isConnected()) {
                try {
                    List<Stream> stream= MqttUpstreamEntity.getInstance(agentId);
                    mqttConnThread.sendMessage(topic, JsonUtils.OBJECT_MAPPER.writeValueAsString(stream));
                    logger.debug("发送的数据为："+JsonUtils.OBJECT_MAPPER.writeValueAsString(stream));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get Topic
     *
     * @param agentId Agent Id
     */
    public static String getTopic(String agentId) {
        return "agents/" + agentId + "/upstream";
    }

}
