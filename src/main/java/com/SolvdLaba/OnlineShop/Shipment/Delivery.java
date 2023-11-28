package com.SolvdLaba.OnlineShop.Shipment;

import com.SolvdLaba.OnlineShop.Interfaces.Deliverable;
import com.SolvdLaba.OnlineShop.Order.Order;
import com.SolvdLaba.OnlineShop.Person.Courier;

public class Delivery implements Deliverable {
    private final String address;
    private final Order order;
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
        System.out.println("Shipping the shipment with details: " + shipment.toString());
        System.out.println("Using courier: " + courier.getName());
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
