package com.solvd.laba.ecommerceshop.shop;


import com.solvd.laba.ecommerceshop.interfaces.Receivable;
import com.solvd.laba.ecommerceshop.interfaces.ShopInterface;
import com.solvd.laba.ecommerceshop.order.exceptions.OrderNotFoundException;
import com.solvd.laba.ecommerceshop.interfaces.Orderable;
import com.solvd.laba.ecommerceshop.order.Order;
import com.solvd.laba.ecommerceshop.order.OrderStatus;
import com.solvd.laba.ecommerceshop.person.Customer;
import com.solvd.laba.ecommerceshop.person.CustomerType;
import com.solvd.laba.ecommerceshop.payment.Payment;
import com.solvd.laba.ecommerceshop.item.exceptions.OutOfStockException;
import com.solvd.laba.ecommerceshop.item.exceptions.ProductNotFoundException;
import com.solvd.laba.ecommerceshop.item.Stock;
import com.solvd.laba.ecommerceshop.shipment.Shipment;
import com.solvd.laba.ecommerceshop.shop.exceptions.InvalidRatingException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;


public class Shop implements ShopInterface, Orderable, Receivable {

    private static final Logger LOGGER = LogManager.getLogger(Shop.class);
    private final String name;
    private List<Order> orders;
    private List<Stock> productList;



    public Shop(String name, List<Stock> productList){
        this.name = name;
        this.productList = productList;
        orders = new LinkedList<>();

        LOGGER.info(String.format("shop created with the name: %s", name));
    }






    public Shop(String name){
        this.name = name;
        productList = new ArrayList<>();
    }

    public static void clientShopRating(int rate){

        if (rate > 10 || rate < 1){
            try{
                throw new InvalidRatingException(rate);
            } catch (InvalidRatingException e){
                e.printStackTrace();
            }
        } else{
            LOGGER.info(String.format("rate: %d", rate));
            System.out.println("Thanks for your feedback!");
        }
    }

    public void showWelcomeMessage(){
        LOGGER.info(
                "Welcome to our beloved %s" +
                        "\nBelow you can find all " +
                        "the available materials:\n", name
        );
    }

    public String getName(){
        return name;
    }

    public List<Stock> getProductList(){
        return productList;
    }

    public void setProductList(List<Stock> productList){
        this.productList = productList;
    }

    public void addProductToShop(Stock stock){
        productList.add(stock);
    }

    public void showGoodbyeMessage(){
        LOGGER.info("Thank you for visiting our shop\n" +
                "You will always be welcome\n" +
                "Best Wishes");
    }

    public void showProductsInShop(){
        productList.stream()
                .map(p -> String.format("\t%s  %d\n", p.getProduct().getName(), p.getProduct().getPrice()))
                .forEach(LOGGER::info);
    }

    public int createOrder(Customer customer){
        Order order = new Order(customer, this);
        order.setOrderId(generateOrderId()); // Set the order ID for the newly created order
        orders.add(order);
        LOGGER.info("Here is your orderId: " + order.getOrderId());
        return order.getOrderId();
    }

    private int generateOrderId() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    @Override
    public void cancelOrder(Customer customer, int orderId) {
        customer.getOrders().stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .ifPresent(order -> {
                    order.setOrderStatus(OrderStatus.CANCELLED);
                    LOGGER.info(String.format("The order number %d was cancelled", orderId));
                });

        System.out.println("order was cancelled");
    }
    public Order searchOrder(int orderId) {
        return orders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElseGet(() -> {
                    try {
                        throw new OrderNotFoundException(orderId);
                    } catch (OrderNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public void buy(int orderId, String nameOfProduct, int quantity){
        Stock product;
        boolean found = false;
        for (Stock stock : productList){
            if (stock.getProduct().getName().equalsIgnoreCase(nameOfProduct)){
                try{
                    found = true;
                    if (stock.getQuantity() < quantity){
                        LOGGER.warn(String.format("The item %s is out of stock", stock.getProduct().getName()));
                        throw new OutOfStockException(nameOfProduct, stock);
                    } else{
                        product = new Stock(stock.getProduct(), quantity);
                        int quantityUpdate = stock.getQuantity() - quantity;
                        stock.setQuantity(quantityUpdate);
                        searchOrder(orderId).addItem(product);
                    }
                } catch (OutOfStockException e){
                    e.printStackTrace();
                }
            }
        }
        if (!found){
            try{
                throw new ProductNotFoundException(this, nameOfProduct);
            } catch (ProductNotFoundException e){
                e.printStackTrace();
            }

        }
    }

    public void showUpdatedOrder(int orderId){
        LOGGER.info("an update of your order list:");
        searchOrder(orderId).showOrder();
        LOGGER.info(searchOrder(orderId).toString());
    }

    public CustomerType upgradeCustomer(int orderId){
        Order order = searchOrder(orderId);
        if (order.getTotal() >= 200 && order.getTotal() < 500){
            order.getCustomer().setCustomerType(CustomerType.INTERMEDIATE);
        } else if (order.getTotal() >= 500){
            order.getCustomer().setCustomerType(CustomerType.PRO);
        }
        return order.getCustomer().getCustomerType();
    }

    protected void applyDiscount(int orderId, CustomerType customerType){
        if (customerType == CustomerType.PRO){
            Order order = searchOrder(orderId);
            int tenPercent = (int) (order.getTotal() * 0.1);
            order.setTotal(order.getTotal() - tenPercent);
        } else if (customerType == CustomerType.INTERMEDIATE){
            Order order = searchOrder(orderId);
            int fivePercent = (int) (order.getTotal() * 0.05);
            order.setTotal(order.getTotal() - fivePercent);
        }
    }

    public void confirmOrder(int orderId, Payment payment){
        Order order = searchOrder(orderId);
        order.calculateTotal();
        applyDiscount(orderId, upgradeCustomer(orderId));
        if (Payment.validateAccount(payment.getAccount()) && payment.pay(order.getTotal())){
            order.setOrderStatus(OrderStatus.CREATED);
            LOGGER.info(order.getOrderStatus());
            LOGGER.info(payment.getPaymentStatus());
            LOGGER.info("Your order has been paid, below you can find your receipt");
            order.printReceipt();
        } else{
            LOGGER.info(payment.getPaymentStatus());
        }
    }

    @Override
    public int hashCode(){
        return this.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        return this.equals(obj);
    }

    @Override
    public void receive(Shipment shipment) {
        if (shipment != null) {
            LOGGER.info("Receiving shipment: " + shipment);
        } else {
            LOGGER.info("Invalid shipment details!");
        }
    }
    // Method to write order details to a file
    public void writeOrderToFile(Order order) {
        File orderFile = new File("orderDetails.txt");

        try {
            FileUtils.writeStringToFile(orderFile, order.toString(), "UTF-8");
            LOGGER.info("order details written to file: " + orderFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString(){
        return "shop{" +
                "name='" + name + '\'' +
                ", productList=" + productList +
                ", order=" + //order +
                '}';
    }


}
