package com.andy.androinfo.beans;

public class Properys extends BaseBean {
    private String key;
    private String value;

    public Properys(String key, String value) {
        this.key = key == null ? "" : key;
        this.value = value == null ? "" : value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
