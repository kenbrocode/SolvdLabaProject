package com.solvd.laba.ecommerceshop.order;

import com.solvd.laba.ecommerceshop.interfaces.Exchangable;
import com.solvd.laba.ecommerceshop.interfaces.Returnable;
import com.solvd.laba.ecommerceshop.person.Customer;
import com.solvd.laba.ecommerceshop.item.Stock;
import com.solvd.laba.ecommerceshop.shop.Shop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Order implements Returnable, Exchangable {
    public static final Logger LOGGER = LogManager.getLogger(Order.class);
    private static int nextOrderId = 1;
    private List<Stock> productList;


    private int orderId;
    private Shop shop;
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
    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
            LOGGER.info("no such Item");
        } else{
            LOGGER.info("removed!");
        }
    }


    public void showOrder() {
        StringBuilder sb = new StringBuilder();
        AtomicInteger counter = new AtomicInteger(1);

        productList.stream()
                .map(product -> String.format("%d.  %s  %d  %d\n", counter.getAndIncrement(), product.getProduct().getName(), product.getQuantity(), product.totalPerProduct()))
                .forEach(sb::append);

        System.out.println(sb);
    }

    public int calculateTotal() {
        return productList.stream()
                .mapToInt(product -> (int) (product.getProduct().getPrice() * product.getQuantity()))
                .sum();
    }

    public int getTotal(){
        return total;
    }

    public void setTotal(int total){
        this.total = total;
    }

    public void printReceipt() {
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        AtomicInteger counter = new AtomicInteger(1);
        String title = String.format("\t%s\n", shop.getName());
        sb.append(title);
        sb.append("-----------------------------------------\n");

        String productsInfo = productList.stream()
                .map(product -> String.format("\t%d.  %s  %d  %d\n", counter.getAndIncrement(), product.getProduct().getName(), product.getProduct().getPrice(), product.getQuantity()))
                .collect(Collectors.joining());

        sb.append(productsInfo);

        sb.append("\n-----------------------------------------\n");
        sb.append(String.format("Name: %s %s\nDate: %s\n", customer.getName(), customer.getSurname(), date));


        System.out.println(sb);
    }

    @Override
    public String toString(){
        return "order{" +
                "orderId=" + getOrderId() +
                ", customer=" + customer.getCustomerId() +
                '}';
    }

    @Override
    public void returnOrder(int orderId) {

        if (orderId > 0) {
            LOGGER.info("Returning order with ID: " + orderId);
        } else {
            LOGGER.info("Invalid order ID!");
        }
    }

    @Override
    public void exchangeOrder(int orderId, String newProduct) {
        if (orderId > 0) {
            LOGGER.info("Exchanging order with ID: " + orderId + " to " + newProduct);
        } else {
            LOGGER.info("Invalid order ID!");
        }


    }
}
