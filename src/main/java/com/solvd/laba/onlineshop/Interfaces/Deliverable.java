package com.solvd.laba.onlineshop.Interfaces;

import com.solvd.laba.onlineshop.Person.Courier;
import com.solvd.laba.onlineshop.Shipment.Shipment;

public interface Deliverable {
    void shipping(Courier courier, Shipment shipment);

}
