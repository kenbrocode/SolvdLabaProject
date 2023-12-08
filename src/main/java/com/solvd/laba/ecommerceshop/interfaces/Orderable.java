package com.solvd.laba.ecommerceshop.interfaces;

import com.solvd.laba.ecommerceshop.person.Customer;

public interface Orderable{
    void cancelOrder(Customer customer, int orderId);
}
