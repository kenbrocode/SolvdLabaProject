package com.SolvdLaba.OnlineShop.Shipment.CustomExceptions;

import com.SolvdLaba.OnlineShop.Shipment.Shipment;

import java.util.Date;

public class ShipmentNotPaidException extends Exception{
    public ShipmentNotPaidException(Shipment shipment){
        super(String.format("The shipment with the order %d was not shipped due to not payment.\nDate:%s", shipment.getOrder().getOrderId(), new Date()));
    }
}
