package com.juanmaya.util;

import org.asciidoctor.ast.Title;

public enum LoginKey {
    NAME("[name]"),
    JOB("[job]"),
    TITLE("[title]"),
    BODY("[body]"),
    USERID("[userId]");



    private final String value;

    LoginKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
