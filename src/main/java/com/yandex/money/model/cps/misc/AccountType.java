package com.yandex.money.model.cps.misc;

public enum AccountType {
    PERSONAL("personal"),
    PROFESSIONAL("professional"),
    UNKNOWN("unknown");

    private final String code;

    AccountType(String code) {
        this.code = code;
    }

    public static AccountType parse(String code) {
        for (AccountType value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }
}
