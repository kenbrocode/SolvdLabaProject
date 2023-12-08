package com.solvd.laba.ecommerceshop.shipment;


import com.solvd.laba.ecommerceshop.order.Order;
import com.solvd.laba.ecommerceshop.order.OrderStatus;
import com.solvd.laba.ecommerceshop.person.Courier;
import com.solvd.laba.ecommerceshop.shipment.exceptions.ShipmentNotPaidException;
import com.solvd.laba.ecommerceshop.shop.Shop;

public class Shipment extends Delivery{
    private Order order;
    private Shop shop;

    public Shipment(String address, Order order, Shop shop){
        super(address, order);
        this.order = order;
        this.shop = shop;
    }

    public static void ship(Courier courier, Shipment shipment){
        if (shipment.getOrder().getOrderStatus() == OrderStatus.CREATED){

            LOGGER.info("order %d has been shipped by the courier: %s\n", shipment.order.getOrderId(), courier.getName());
            courier.removeShipment(shipment);
        } else{
            try{
                throw new ShipmentNotPaidException(shipment);
            } catch (ShipmentNotPaidException e){
                e.printStackTrace();
            }
        }
    }

    public Order getOrder(){
        return order;
    }

    public Shop getShop(){
        return shop;
    }

    public void ship(){
        LOGGER.info("shipped");
    }
}
