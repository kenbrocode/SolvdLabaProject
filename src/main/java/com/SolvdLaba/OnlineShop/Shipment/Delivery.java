package com.SolvdLaba.OnlineShop.Shipment;

import com.SolvdLaba.OnlineShop.Interfaces.Deliverable;
import com.SolvdLaba.OnlineShop.Order.Order;
import com.SolvdLaba.OnlineShop.Person.Courier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Delivery implements Deliverable {
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
