package com.solvd.laba.ecommerceshop.shipment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class Delivery {
    public static final Logger LOGGER = LogManager.getLogger(Delivery.class);
    protected String address;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
