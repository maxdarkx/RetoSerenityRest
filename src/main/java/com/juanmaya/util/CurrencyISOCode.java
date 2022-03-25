package com.juanmaya.util;

public enum CurrencyISOCode {
    MONEY_ISO_CODE("[moneyIsoCode]");

    private final String value;

    CurrencyISOCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
