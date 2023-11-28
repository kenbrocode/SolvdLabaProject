package com.SolvdLaba.OnlineShop.Shipment;


import com.SolvdLaba.OnlineShop.Order.Order;
import com.SolvdLaba.OnlineShop.Order.OrderStatus;
import com.SolvdLaba.OnlineShop.Person.Courier;
import com.SolvdLaba.OnlineShop.Shipment.CustomExceptions.ShipmentNotPaidException;
import com.SolvdLaba.OnlineShop.Shop.Shop;

public class Shipment extends Delivery{
    private final Order order;
    private final Shop shop;

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
