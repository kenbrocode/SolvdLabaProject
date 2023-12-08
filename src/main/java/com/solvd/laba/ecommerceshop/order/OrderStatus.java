package com.solvd.laba.ecommerceshop.order;

public enum OrderStatus {
    CREATED("Created"),
    CANCELLED("Cancelled"),
    IN_PROGRESS("In Progress"),
    DELIVERED("Delivered");

    private final String statusName;

    OrderStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;

    }
}
