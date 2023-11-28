package com.SolvdLaba.OnlineShop.Order;

import com.SolvdLaba.OnlineShop.Interfaces.Exchangable;
import com.SolvdLaba.OnlineShop.Interfaces.Returnable;
import com.SolvdLaba.OnlineShop.Person.Customer;
import com.SolvdLaba.OnlineShop.Product.Stock;
import com.SolvdLaba.OnlineShop.Shop.Shop;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Order implements Returnable, Exchangable {

    private static int nextOrderId = 1;
    private final List<Stock> productList;
    private final int orderId;
    private final Shop shop;
    private OrderStatus orderStatus;
    private int total = 0;
    private Customer customer;

    public Order(Customer customer, Shop shop){
        this.productList = new ArrayList<>();
        this.orderStatus = OrderStatus.CREATED;
        this.orderId = nextOrderId++;
        this.customer = customer;
        this.shop = shop;
        customer.getOrders().add(this);
    }

    public int getOrderId(){
        return orderId;
    }

    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public OrderStatus getOrderStatus(){
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }

    public List<Stock> getProductList(){
        return productList;
    }

    public void addItem(Stock product){
        productList.add(product);
    }

    public void removeItem(Stock product){
        if (!productList.remove(product)){
            System.out.println("no such Item");
        } else{
            System.out.println("removed!");
        }
    }


    public void showOrder(){
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (Stock product : productList){
            sb.append(String.format("%d.  %s  %d  %d\n", counter, product.getProduct().getName(), product.getQuantity(), product.totalPerProduct()));
            counter++;
        }

        System.out.println(sb);
    }

    public int CalculateTotal(){
        for (Stock product : productList){
            total += ((long) product.getProduct().getPrice() * product.getQuantity());
        }

        return total;
    }

    public int getTotal(){
        return total;
    }

    public void setTotal(int total){
        this.total = total;
    }

    public void printReceipt(){
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        String title = String.format("\t%s\n", shop.getName());
        sb.append(title);
        sb.append("-----------------------------------------\n");
        for (Stock product : productList){
            sb.append(String.format("\t%d.  %s  %d  %d", counter, product.getProduct().getName(), product.getProduct().getPrice(), product.getQuantity()));
            sb.append("\n");
            counter++;
        }
        sb.append("\n");
        sb.append("-----------------------------------------\n");
        sb.append(String.format("Name: %s %s\nDate: %s\n", customer.getName(), customer.getSurname(), date));
        sb.append(String.format("Total: %d\n\tPAID", getTotal()));

        System.out.println(sb);
    }

    @Override
    public String toString(){
        return "Order{" +
                "orderId=" + getOrderId() +
                ", customer=" + customer.getCustomerId() +
                '}';
    }

    @Override
    public void returnOrder(int orderId) {

        if (orderId > 0) {
            System.out.println("Returning order with ID: " + orderId);
        } else {
            System.out.println("Invalid order ID!");
        }
    }

    @Override
    public void exchangeOrder(int orderId, String newProduct) {
        if (orderId > 0) {
            System.out.println("Exchanging order with ID: " + orderId + " to " + newProduct);
        } else {
            System.out.println("Invalid order ID!");
        }


    }
}
