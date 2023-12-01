package com.solvd.laba.ecommerceshop.Interfaces;

import com.solvd.laba.ecommerceshop.Person.Courier;
import com.solvd.laba.ecommerceshop.Shipment.Shipment;

public interface Deliverable {
    void shipping(Courier courier, Shipment shipment);

}
