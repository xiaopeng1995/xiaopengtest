package io.j1st.storage.entity;

import java.util.Map;

/**
 * Upstream or Downstream
 */
public class Stream {

    private String hwid;
    private DeviceType type;
    private Map<String ,Object> values;

    public String getHwid() {
        return hwid;
    }

    public void setHwid(String hwid) {
        this.hwid = hwid;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public Map<String ,Object> getValues() {
        return values;
    }

    public void setValues(Map values) {
        this.values = values;
    }
}
