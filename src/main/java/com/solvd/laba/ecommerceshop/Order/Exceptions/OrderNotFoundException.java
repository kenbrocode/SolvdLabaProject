package com.solvd.laba.ecommerceshop.Order.Exceptions;

public class OrderNotFoundException extends Exception{

    public OrderNotFoundException(int numberOfOrder){
        super(String.format("Sorry, no order with the %d is available on the system.", numberOfOrder));
    }
}
