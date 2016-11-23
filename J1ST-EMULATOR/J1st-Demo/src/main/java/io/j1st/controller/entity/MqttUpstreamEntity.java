package io.j1st.controller.entity;

import io.j1st.storage.MongoStorage;
import io.j1st.storage.entity.DeviceType;
import io.j1st.storage.entity.GenData;
import io.j1st.storage.entity.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.Document;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Mqtt upstream
 */
public class MqttUpstreamEntity {

    private static MongoStorage mongoStorage;

    public MqttUpstreamEntity(MongoStorage mongoStorage){
        this.mongoStorage=mongoStorage;
    }

    // upstream object
    public static List<Stream> getInstance(String agentId,String type){
        List<Stream> streams=new ArrayList<>();
        streams.add(getAgentStream(agentId,type));
        //update device
        //streams.add(getDeviceStream(agentId));
        //insert device
        //streams.add(getDeviceStream());
        return streams;
    }

    // agent stream
    public static Stream getAgentStream(String agentId,String type){
        Stream agentStreams=new Stream();
//        //agentStreams.setHwid(agentId);
        agentStreams.setType(DeviceType.AGENT);
        agentStreams.setModel("Omnik_one");
        agentStreams.setDsn(agentId);
        Map<String,Object> map=new HashMap<>();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");//可以方便地修改日期格式
        String date = dateFormat.format( now );
        System.out.println(date);
        Document document = mongoStorage.findGendDataBytime(date,0);
        if(type.equals("batP"))
        {
            map.put("BatP", document.get("batP"));
            map.put("BatSOC", document.get("batSOC"));
        }
        else if(type.equals("load"))
        {
            map.put("PowerT", document.get("powerT"));
            map.put("MeterT", document.get("meterT"));
        }
        else if(type.equals("grid"))
        {
            map.put("PowerG", document.get("powerG"));
            map.put("MeterG", document.get("meterG"));
        }
        else if(type.equals("car"))
        {
            map.put("Car1P",document.get("car1P"));
            map.put("Car1SOC",document.get("car1SOC"));
            map.put("Car2P",document.get("car2P"));
            map.put("Car2SOC",document.get("car2SOC"));
        }
        else if(type.equals("pVP"))
        {
            map.put("PVPower",document.get("pVPower"));
            map.put("EToday",document.get("eToday"));
        }
        agentStreams.setValues(map);
        return agentStreams;
    }

    // Device stream
    public static Stream getDeviceStream(String agentId){
        Stream deviceStreams=new Stream();
        // insert device
        //deviceStreams.setDsn(RandomStringUtils.randomAlphabetic(32));
        // update device
        deviceStreams.setDsn(agentId);
        deviceStreams.setType(DeviceType.INVERTER);
        Map<String,Object> map=new HashMap<>();
        map.put("pac",600);
        map.put("etoday",Math.incrementExact(2));
        map.put("etotal",Math.incrementExact(10));
        map.put("Vpv1",113.9);
        map.put("Vpv2",116.6);
        map.put("Ipv1",11.33);
        map.put("Ipv2",12.68);
        map.put("InvMode",1);
        map.put("PV1Fault",2000);
        map.put("GFCIFault",10000);
        deviceStreams.setValues(map);
        return deviceStreams;
    }


    public MongoStorage getMongoStorage() {
        return mongoStorage;
    }

    public void setMongoStorage(MongoStorage mongoStorage) {
        this.mongoStorage = mongoStorage;
    }


}
