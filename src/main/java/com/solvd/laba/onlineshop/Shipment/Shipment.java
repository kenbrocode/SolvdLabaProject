package com.solvd.laba.onlineshop.Shipment;


import com.solvd.laba.onlineshop.Order.Order;
import com.solvd.laba.onlineshop.Order.OrderStatus;
import com.solvd.laba.onlineshop.Person.Courier;
import com.solvd.laba.onlineshop.Shipment.CustomExceptions.ShipmentNotPaidException;
import com.solvd.laba.onlineshop.Shop.Shop;

public class Shipment extends Delivery{
    private Order order;
    private Shop shop;

    public Shipment(String address, Order order, Shop shop){
        super(address, order);
        this.order = order;
        this.shop = shop;
    }

    public static void ship(Courier courier, Shipment shipment){
        if (shipment.getOrder().getOrderStatus() == OrderStatus.CONFIRMED){

            LOGGER.info("Order %d has been shipped by the courier: %s\n", shipment.order.getOrderId(), courier.getName());
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
