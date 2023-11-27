package com.SolvdLaba.OnlineShop.Shipment;

import com.SolvdLaba.OnlineShop.Order.Order;

public class Delivery{
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
