package com.SolvdLaba.OnlineShop.Interfaces;

import com.SolvdLaba.OnlineShop.Person.Customer;

public interface Orderable{
    void cancelOrder(Customer customer, int orderId);
}
