package com.solvd.laba.ecommerceshop.Interfaces;

import com.solvd.laba.ecommerceshop.Person.Customer;

public interface Orderable{
    void cancelOrder(Customer customer, int orderId);
}
