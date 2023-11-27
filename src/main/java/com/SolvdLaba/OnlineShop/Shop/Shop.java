package com.SolvdLaba.OnlineShop.Shop;


import com.SolvdLaba.OnlineShop.Order.Exceptions.OrderNotFoundException;
import com.SolvdLaba.OnlineShop.Order.Interfaces.Orderable;
import com.SolvdLaba.OnlineShop.Order.Order;
import com.SolvdLaba.OnlineShop.Order.OrderStatus;
import com.SolvdLaba.OnlineShop.Person.Customer;
import com.SolvdLaba.OnlineShop.Person.CustomerType;
import com.SolvdLaba.OnlineShop.Payment.Payment;
import com.SolvdLaba.OnlineShop.Product.Exceptions.OutOfStockException;
import com.SolvdLaba.OnlineShop.Product.Exceptions.ProductNotFoundException;
import com.SolvdLaba.OnlineShop.Product.Stock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Shop implements Orderable {

    private static final Logger LOGGER = LogManager.getLogger(Shop.class);
    private final String name;
    private LinkedList<Order> orders;
    private List<Stock> productList;


    public Shop(String name, List<Stock> productList){
        this.name = name;
        this.productList = productList;
        orders = new LinkedList<>();
        LOGGER.info(String.format("Shop created with the name: %s", name));
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
        System.out.printf(
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
        System.out.println("Thank you for visiting our shop\n" +
                "You will always be welcome\n" +
                "Best Wishes");
    }

    public void showProductsInShop(){
        productList.forEach(p -> System.out.printf("\t%s  %d\n", p.getProduct().getName(), p.getProduct().getPrice()));
    }

    public int createOrder(Customer customer){
        Order order = new Order(customer, this);
        orders.add(order);
        System.out.printf("Here is your orderId: %d\n", order.getOrderId());
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
        System.out.println("an update of your order list:");
        searchOrder(orderId).showOrder();
        System.out.println(searchOrder(orderId).toString());
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

    private void applyDiscount(int orderId, CustomerType customerType){
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
            System.out.println(order.getOrderStatus());
            System.out.println(payment.getPaymentStatus());
            System.out.println("Your order has been paid, below you can find your receipt");
            order.printReceipt();
        } else{
            System.out.println(payment.getPaymentStatus());
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
    public String toString(){
        return "Shop{" +
                "name='" + name + '\'' +
                ", productList=" + productList +
                ", order=" + //order +
                '}';
    }


}
