package com.juanmaya.model;

import java.util.HashMap;
import java.util.Map;

public class DataUser {

    private Data data;
    private Support support;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

