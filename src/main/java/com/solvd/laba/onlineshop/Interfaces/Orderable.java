package com.solvd.laba.onlineshop.Interfaces;

import com.solvd.laba.onlineshop.Person.Customer;

public interface Orderable{
    void cancelOrder(Customer customer, int orderId);
}
