package io.j1st.controller.entity;

import io.j1st.controller.mqtt.MqttConnThread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Mqtt 客户端连接注册表
 */
public enum Registry {

    // instance
    INSTANCE;

    public Map<String, MqttConnThread> map = new ConcurrentHashMap<>();
    public Map<String, String> Type = new ConcurrentHashMap<>();

    // connect pool
    private final ExecutorService es = Executors.newFixedThreadPool(5000);

    // save session
    public void saveSession(String agentId, MqttConnThread client) {
        this.map.put(agentId, client);
    }

    // save productType
    public void saveType(String agentId, String productType) {
        this.Type.put(agentId, productType);
    }

    // get session
    public Map<String, MqttConnThread> getSession() {
        return this.map;
    }

    // get tyoe
    public Map<String, String> getType() {
        return this.Type;
    }

    // delete Session
    public void deleteSession(String agentId) {
        this.map.remove(agentId);
    }

    // Start Thread
    public void startThread(MqttConnThread client) {
        this.es.submit(client);
    }
}
