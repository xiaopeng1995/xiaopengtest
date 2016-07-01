package io.j1st.controller.entity;

import io.j1st.storage.entity.DeviceType;
import io.j1st.storage.entity.Stream;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * Mqtt upstream
 */
public class MqttUpstreamEntity {

    // upstream object
    public static List<Stream> getInstance(String agentId){
        List<Stream> streams=new ArrayList<>();
        streams.add(getAgentStream(agentId));
        streams.add(getDeviceStream());
        return streams;
    }

    // agent stream
    public static Stream getAgentStream(String agentId){
        Stream agentStreams=new Stream();
        agentStreams.setHwid(agentId);
        agentStreams.setType(DeviceType.AGENT);
        Map<String,Object> map=new HashMap<>();
        map.put("interval",300);
        map.put("mod","IUNI N1");
        map.put("Manufacturer","ZenInfo Co.");
        map.put("PtVer","Omnik_V1.1.1");
        map.put("Interval4modbus",10);
        map.put("ConfigTs", 1464676561758L);
        map.put("FreeSpace", 712);
        agentStreams.setValues(map);
        return agentStreams;
    }

    // Device stream
    public static Stream getDeviceStream(){
        Stream deviceStreams=new Stream();
        deviceStreams.setHwid(RandomStringUtils.randomAlphabetic(32));
        deviceStreams.setType(DeviceType.INVERTER);
        Map<String,Object> map=new HashMap<>();
        map.put("pac",11);
        map.put("etoday",1200);
        map.put("Vpv1",636.9);
        map.put("Vpv2",639.6);
        map.put("Ipv1",11.33);
        map.put("Ipv2",12.68);
        map.put("InvMode",1);
        map.put("PV1Fault",2000);
        map.put("GFCIFault",10000);
        deviceStreams.setValues(map);
        return deviceStreams;
    }

}
