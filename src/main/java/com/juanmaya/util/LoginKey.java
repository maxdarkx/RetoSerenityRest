package com.juanmaya.util;

public enum LoginKey {
    NAME("[name]"),
    JOB("[job]");

    private final String value;

    LoginKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
