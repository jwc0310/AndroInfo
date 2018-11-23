package com.andy.androinfo.beans;

public class Properys extends BaseBean {
    private String key;
    private String value;
    private String realValue;

    public Properys(String key, String value) {
        this.key = key == null ? "" : key;
        this.value = value == null ? "" : value;
    }

    public Properys(String key, String value, String realValue) {
        this(key, value);
        this.realValue = realValue == null ? "" : value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getRealValue() {
        return realValue;
    }
}
