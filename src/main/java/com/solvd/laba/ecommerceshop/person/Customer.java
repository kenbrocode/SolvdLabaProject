package com.solvd.laba.ecommerceshop.person;

import com.solvd.laba.ecommerceshop.order.Order;

import java.util.LinkedList;
import java.util.List;

public class Customer extends Person{

    private static int nextCustomerId = 1;
    private final int customerId;
    private CustomerType customerType = CustomerType.BASIC;
    private List<Order> orders;
    private String address;

    public Customer(String clientFirstName, String clientSurname){
        super(clientFirstName, clientSurname);
        this.customerId = nextCustomerId++;
        orders = new LinkedList<>();
    }

    public int getCustomerId(){
        return customerId;
    }

    public List<Order> getOrders(){
        return orders;
    }

    public void setOrders(LinkedList<Order> orders){
        this.orders = orders;
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    public CustomerType getCustomerType(){
        return customerType;
    }

    public void setCustomerType(CustomerType customerType){
        this.customerType = customerType;
    }

    @Override
    public boolean equals(Object obj){
        return super.equals(obj);
    }

    @Override
    public String toString(){
        return String.format("%s %s(%d)", getName(), getSurname(), getCustomerId());
    }


}
