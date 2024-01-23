package com.mstockRestAPI.mstockRestAPI.enums;

public enum Currency {
    AZN("AZERBAIJAN MANAT"),
    USD("United States Dollar"),
    EUR("Euro"),
    GBP("British Pound Sterling"),
    JPY("Japanese Yen"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CNY("Chinese Yuan"),
    INR("Indian Rupee"),
    BRL("Brazilian Real"),
    ZAR("South African Rand");

    private final String fullName;

    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
