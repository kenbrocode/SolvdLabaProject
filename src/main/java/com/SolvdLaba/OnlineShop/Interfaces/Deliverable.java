package com.SolvdLaba.OnlineShop.Interfaces;

import com.SolvdLaba.OnlineShop.Person.Courier;
import com.SolvdLaba.OnlineShop.Shipment.Shipment;

public interface Deliverable {
    void shipping(Courier courier, Shipment shipment);

}
