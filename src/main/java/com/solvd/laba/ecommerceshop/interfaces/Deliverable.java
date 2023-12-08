package com.solvd.laba.ecommerceshop.interfaces;

import com.solvd.laba.ecommerceshop.person.Courier;
import com.solvd.laba.ecommerceshop.shipment.Shipment;

public interface Deliverable {
    void shipping(Courier courier, Shipment shipment);

}
