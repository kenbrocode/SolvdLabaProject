package com.SolvdLaba.OnlineShop.Person;

import com.SolvdLaba.OnlineShop.Order.Order;

import java.util.LinkedList;

public class Customer extends Person{

    private static int nextCustomerId = 1;
    private final int customerId;
    private CustomerType customerType = CustomerType.BASIC;
    private LinkedList<Order> orders;

    public Customer(String clientFirstName, String clientSurname){
        super(clientFirstName, clientSurname);
        this.customerId = nextCustomerId++;
        orders = new LinkedList<>();
    }

    public int getCustomerId(){
        return customerId;
    }

    public LinkedList<Order> getOrders(){
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
