package com.SolvdLaba.OnlineShop;


import com.SolvdLaba.OnlineShop.Person.Courier;
import com.SolvdLaba.OnlineShop.Person.Customer;
import com.SolvdLaba.OnlineShop.Shop.Shop;
import com.SolvdLaba.OnlineShop.Product.Category;
import com.SolvdLaba.OnlineShop.Product.Product;
import com.SolvdLaba.OnlineShop.Payment.Account;
import com.SolvdLaba.OnlineShop.Payment.AccountType;
import com.SolvdLaba.OnlineShop.Payment.Payment;
import com.SolvdLaba.OnlineShop.Product.Stock;
import com.SolvdLaba.OnlineShop.Shipment.Shipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RunShop();
    }

    public static void RunShop() {
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



        System.out.println("product list: "+productList);



        Shop shop = new Shop("Feels Good Shop", productList);
        shop.showWelcomeMessage();
        shop.showProductsInShop();
        try (Scanner scanner = new Scanner(System.in)) {

        System.out.println("Would you like to create an order?\n\t1- yes\n\t0- no");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Insert your name and surname");
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
        } } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
