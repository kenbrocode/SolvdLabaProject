package com.solvd.laba.ecommerceshop.shipment;

import com.solvd.laba.ecommerceshop.interfaces.Deliverable;
import com.solvd.laba.ecommerceshop.order.Order;
import com.solvd.laba.ecommerceshop.person.Courier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class Delivery implements Deliverable {
    public static final Logger LOGGER = LogManager.getLogger(Delivery.class);
    private String address;
    private Order order;


    public Delivery(String address, Order order){
        this.address = address;
        this.order = order;
    }

    public Order getOrder(){
        return order;
    }

    @Override
    public void shipping(Courier courier, Shipment shipment) {
        LOGGER.info("Shipping the shipment with details: " + shipment.toString());
        LOGGER.info("Using courier: " + courier.getName());
    }



    public String getAddress(){
        return address;
    }
}
