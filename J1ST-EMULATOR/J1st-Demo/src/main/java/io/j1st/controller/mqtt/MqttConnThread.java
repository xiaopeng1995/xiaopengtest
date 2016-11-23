package io.j1st.controller.mqtt;


import com.mongodb.util.JSON;
import io.j1st.controller.entity.MqttUpstreamEntity;
import io.j1st.controller.entity.Registry;
import io.j1st.controller.util.JsonUtils;
import io.j1st.storage.entity.Stream;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;


/**
 * Mqtt Connect Controller
 */
public class MqttConnThread implements Callable {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(MqttConnThread.class);

    // Mqtt Client
    private MqttClient mqttClient;

    // Mqtt Connect Options
    private MqttConnectOptions options;

    // Construction
    public MqttConnThread(MqttClient mqttClient, MqttConnectOptions options) {
        this.mqttClient = mqttClient;
        this.options = options;
    }

    @Override
    public Object call() throws Exception {
        try {
            // connect mqtt broker
            mqttClient.connect(options);
            mqttClient.setTimeToWait(2000);

            //判断客户端是否连接上
            if (mqttClient.isConnected()) {
                mqttClient.setCallback(new MqttCallback() {

                    @Override
                    public void connectionLost(Throwable cause) {
                        logger.debug("线程:{}断开连接，开始重连", mqttClient.getClientId());
                        Registry.INSTANCE.startThread(new MqttConnThread(mqttClient, options));
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        logger.debug("收到的消息为：" + message.toString());
                        if (message.toString().contains("ems_upload")) {
                            logger.debug("ems_upload收到的消息为：" + message.toString());
                            String agentId = topic.replace("agents/", "").replace("/downstream", "").trim();
                            List<Stream> stream = null;
                            stream = MqttUpstreamEntity.getInstance(agentId, Registry.INSTANCE.getType().get(agentId));
                            logger.debug(agentId);
                            logger.debug("topic:{}", topic);
                            String upmessage = JsonUtils.OBJECT_MAPPER.writeValueAsString(stream);
                            //mqttClient = Registry.INSTANCE.getSession().get(agentId).mqttClient;
                            mqttClient.publish("agents/" + agentId + "/ems_upload", new MqttMessage(upmessage.getBytes("utf-8")));
                            logger.debug("发送的数据为：" + upmessage);
                        }
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {
                        logger.debug("asdsadsadasd");
                    }
                });

                String topic = "agents/" + mqttClient.getClientId() + "/downstream";
                mqttClient.subscribe(topic);
            }
            logger.debug("后台mqtt客户端:{}连接服务器 broker成功！", mqttClient.getClientId());
        } catch (Exception e) {
            logger.error("后台mqtt客户端:{}连接服务器 broker失败！重新连接开始...", mqttClient.getClientId());
            Registry.INSTANCE.startThread(new MqttConnThread(mqttClient, options));
            //每个5秒连接一次
            Thread.sleep(5000);
        }
        return null;
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public MqttConnectOptions getOptions() {
        return options;
    }

    public void setOptions(MqttConnectOptions options) {
        this.options = options;
    }

    //发布消息
    public void sendMessage(String topic, String message) {
        try {
            this.mqttClient.publish(topic, new MqttMessage(message.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
