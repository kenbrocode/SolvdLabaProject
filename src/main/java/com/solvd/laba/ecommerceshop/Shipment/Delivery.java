package com.solvd.laba.ecommerceshop.Shipment;

import com.solvd.laba.ecommerceshop.Interfaces.Deliverable;
import com.solvd.laba.ecommerceshop.Order.Order;
import com.solvd.laba.ecommerceshop.Person.Courier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class Delivery implements Deliverable {
    public static final Logger LOGGER = LogManager.getLogger(Delivery.class);
    private String address;
    private Order order;
    private int deliveryPrice = 15;

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

    public int getDeliveryPrice(){
        return deliveryPrice;
    }

    public void setDeliveryPrice(int deliveryPrice){
        this.deliveryPrice = deliveryPrice;
    }

    public String getAddress(){
        return address;
    }
}
