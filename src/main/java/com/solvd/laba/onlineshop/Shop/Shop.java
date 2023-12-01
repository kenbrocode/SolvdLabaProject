package com.solvd.laba.onlineshop.Shop;


import com.solvd.laba.onlineshop.Interfaces.Receivable;
import com.solvd.laba.onlineshop.Interfaces.ShopInterface;
import com.solvd.laba.onlineshop.Order.Exceptions.OrderNotFoundException;
import com.solvd.laba.onlineshop.Interfaces.Orderable;
import com.solvd.laba.onlineshop.Order.Order;
import com.solvd.laba.onlineshop.Order.OrderStatus;
import com.solvd.laba.onlineshop.Person.Customer;
import com.solvd.laba.onlineshop.Person.CustomerType;
import com.solvd.laba.onlineshop.Payment.Payment;
import com.solvd.laba.onlineshop.Product.Exceptions.OutOfStockException;
import com.solvd.laba.onlineshop.Product.Exceptions.ProductNotFoundException;
import com.solvd.laba.onlineshop.Product.Stock;
import com.solvd.laba.onlineshop.Shipment.Shipment;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Shop implements ShopInterface, Orderable, Receivable {

    private static final Logger LOGGER = LogManager.getLogger(Shop.class);
    private final String name;
    private LinkedList<Order> orders;
    private List<Stock> productList;
    private Map<Integer, Order> ordersMap = new HashMap<>();


    public Shop(String name, List<Stock> productList){
        this.name = name;
        this.productList = productList;
        orders = new LinkedList<>();
        ordersMap = new HashMap<>();
        LOGGER.info(String.format("Shop created with the name: %s", name));
    }

    public void addOrder(int orderId, Order order) {
        ordersMap.put(orderId, order);
    }

    public Order getOrderById(int orderId) {
        return ordersMap.get(orderId);
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
        productList.forEach(p -> LOGGER.info("\t%s  %d\n", p.getProduct().getName(), p.getProduct().getPrice()));
    }

    public int createOrder(Customer customer){
        Order order = new Order(customer, this);
        orders.add(order);
        LOGGER.info("Here is your orderId: %d\n", order.getOrderId());
        return order.getOrderId();
    }

    @Override
    public void cancelOrder(Customer customer, int orderId){
        for (Order order : customer.getOrders()){
            if (order.getOrderId() == orderId){
                order.setOrderStatus(OrderStatus.CANCELLED);
                LOGGER.info(String.format("The order number %d was cancelled", orderId));
            }
        }
        System.out.println("Order was cancelled");
    }

    public Order searchOrder(int orderId){
        for (Order order : orders){
            if (order.getOrderId() == orderId){
                return order;
            } else{
                try{
                    throw new OrderNotFoundException(orderId);
                } catch (OrderNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void buy(int orderId, String nameOfProduct, int quantity){
        Stock product;
        boolean found = false;
        for (Stock stock : productList){
            if (stock.getProduct().getName().equalsIgnoreCase(nameOfProduct)){
                try{
                    found = true;
                    if (stock.getQuantity() < quantity){
                        LOGGER.warn(String.format("The product %s is out of stock", stock.getProduct().getName()));
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
        order.CalculateTotal();
        applyDiscount(orderId, upgradeCustomer(orderId));
        if (Payment.validateAccount(payment.getAccount()) && payment.pay(order.getTotal())){
            order.setOrderStatus(OrderStatus.CONFIRMED);
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
            LOGGER.info("Order details written to file: " + orderFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString(){
        return "Shop{" +
                "name='" + name + '\'' +
                ", productList=" + productList +
                ", order=" + //order +
                '}';
    }


}
