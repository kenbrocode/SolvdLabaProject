package com.solvd.laba.onlineshop.Shipment.CustomExceptions;

import com.solvd.laba.onlineshop.Shipment.Shipment;

import java.util.Date;

public class ShipmentNotPaidException extends Exception{
    public ShipmentNotPaidException(Shipment shipment){
        super(String.format("The shipment with the order %d was not shipped due to not payment.\nDate:%s", shipment.getOrder().getOrderId(), new Date()));
    }
}
