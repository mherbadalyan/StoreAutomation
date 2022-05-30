package com.example.storeautomation.model.enums;

public enum StoreStatus {
    GENERAL("General"),
    BRANCH("Branch");

    private final String name;

    StoreStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
