package com.solvd.laba.onlineshop;


import com.solvd.laba.onlineshop.Interfaces.ShopInterface;
import com.solvd.laba.onlineshop.Person.Courier;
import com.solvd.laba.onlineshop.Person.Customer;
import com.solvd.laba.onlineshop.Shop.Shop;
import com.solvd.laba.onlineshop.Product.Category;
import com.solvd.laba.onlineshop.Product.Product;
import com.solvd.laba.onlineshop.Payment.Account;
import com.solvd.laba.onlineshop.Payment.AccountType;
import com.solvd.laba.onlineshop.Payment.Payment;
import com.solvd.laba.onlineshop.Product.Stock;
import com.solvd.laba.onlineshop.Shipment.Shipment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {


//adding products from different cathegories
        List<Stock> productList = new ArrayList<>();
        Product product1 = new Product("Chair", 50, Category.HOMEGOODS);
        Stock stock1 = new Stock(product1,50);
        productList.add(stock1);

        Product product2 = new Product("Desk", 100, Category.HOMEGOODS);
        Stock stock2 = new Stock(product2, 30);
        productList.add(stock2);

        Product product3 = new Product("Lamp", 25, Category.HOMEGOODS);
        Stock stock3 = new Stock(product3, 20);
        productList.add(stock3);

        Product product4 = new Product("Historybook", 100, Category.BOOKS);
        Stock stock4 = new Stock(product4, 15);
        productList.add(stock4);

        Product product5 = new Product("Smartphone", 500, Category.ELECTRONICS);
        Stock stock5 = new Stock(product5, 10);
        productList.add(stock5);

        System.out.println("Products in stock");
        for (Stock product : productList) {
            System.out.println(product.getProduct());
        }



        Shop shop = new Shop("Feels Good Shop", productList);
        shop.showWelcomeMessage();
        shop.showProductsInShop();
        try (Scanner scanner = new Scanner(System.in)) {

        System.out.println("Would you like to create an order?\n\t1- yes\n\t0- no");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Insert your name and last name");
            String clientName = scanner.next();
            String clientSurName = scanner.next();
            Customer customer = new Customer(clientName, clientSurName);

            int orderId = shop.createOrder(customer);
            shop.addOrderToMap(orderId, shop.searchOrder(orderId));

            boolean stop = false;

            do {
                System.out.println("Which items would you like to buy? Insert its name and quantity");
                String productName = scanner.next();
                int productQuantity = scanner.nextInt();
                shop.buy(orderId, productName, productQuantity);
                System.out.println("If you want to buy more press 0, if not press one!");
                choice = scanner.nextInt();
                if (choice == 1) {
                    stop = true;
                }
            } while (!stop);

            System.out.println("An update on your order:");
            shop.showUpdatedOrder(orderId);


            System.out.println("Payment ");
            System.out.println("Provide your card number:");
            int cardNumber = scanner.nextInt();
            System.out.println("Provide your CVV:");
            int cvv = scanner.nextInt();
            System.out.println("Provide your card expiration date (MM/YY):");
            String expirationDate = scanner.next();
            System.out.println("Provide your street name:");
            String shippingAddress = scanner.next();


            Account account = new Account(cardNumber, cvv, expirationDate, AccountType.VISA, 100);
            account.topUp(500);
            Payment payment = new Payment(account, customer, shop.searchOrder(orderId), shippingAddress);
            shop.confirmOrder(orderId, payment);

            System.out.println("Your payment has been processed.");

            Shipment shipment = new Shipment(shippingAddress, shop.searchOrder(orderId), shop);
            Courier courier = new Courier("Ken", "Carter", "5555555");
            courier.addShipment(shipment);
            Shipment.ship(courier, shipment);

            System.out.println("How was your experience in the shop from 1-10");
            int rate = scanner.nextInt();
            Shop.clientShopRating(rate);
            scanner.close();
        } else {
            shop.showWelcomeMessage();
            shop.showProductsInShop();
            shop.showGoodbyeMessage();
            System.out.println("How was your experience in the shop from 1-10");
            int rate = scanner.nextInt();

            Shop.clientShopRating(rate);
            scanner.close();
        } } catch (Exception e) {
            LOGGER.error("An error occurred: " + e.getMessage(), e);
            }
        }

    }
