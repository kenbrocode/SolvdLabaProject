package com.solvd.laba.ecommerceshop.order.exceptions;

public class OrderNotFoundException extends Exception{

    public OrderNotFoundException(int numberOfOrder){
        super(String.format("Sorry, no order with the %d is available on the system.", numberOfOrder));
    }
}
