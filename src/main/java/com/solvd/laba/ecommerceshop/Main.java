package com.solvd.laba.ecommerceshop;


import com.solvd.laba.ecommerceshop.person.Courier;
import com.solvd.laba.ecommerceshop.person.Customer;
import com.solvd.laba.ecommerceshop.shop.Shop;
import com.solvd.laba.ecommerceshop.item.Category;
import com.solvd.laba.ecommerceshop.item.Product;
import com.solvd.laba.ecommerceshop.payment.Account;
import com.solvd.laba.ecommerceshop.payment.AccountType;
import com.solvd.laba.ecommerceshop.payment.Payment;
import com.solvd.laba.ecommerceshop.item.Stock;
import com.solvd.laba.ecommerceshop.shipment.Shipment;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        String accountsFilePath = "src/main/resources/accounts";
        InitializeAccounts.initializeAccountsFromFile(accountsFilePath);


        List<Stock> productList = new ArrayList<>();

        // Using reflection to create instances of products and add them to the productList
        productList.add(new Stock(Product.createProduct("Chair", 50, Category.HOMEGOODS), 50));
        productList.add(new Stock(Product.createProduct("Desk", 100, Category.HOMEGOODS), 30));
        productList.add(new Stock(Product.createProduct("Lamp", 25, Category.HOMEGOODS), 20));
        productList.add(new Stock(Product.createProduct("Algebra", 100, Category.BOOKS), 15));
        productList.add(new Stock(Product.createProduct("Smartphone", 500, Category.ELECTRONICS), 10));



      System.out.println("Products in stock:");
        productList.stream()
                .map(Stock::getProduct)
                .forEach(System.out::println);



        Shop shop = new Shop("Feels Good shop", productList);
        shop.showWelcomeMessage();
        shop.showProductsInShop();
        try (Scanner scanner = new Scanner(System.in)) {

      System.out.println("1. Press 1 to create an order ");
      System.out.println("2. Press 2 to search for a item by name ");
      System.out.println("3. Press 3 to filter products based on a max price threshold ");
      System.out.println("4. Press 4 to retrieve products within a price range ");
      System.out.println("5. Press 5 to retrieve all products from a specific category ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Insert your name and last name");
            String clientName = scanner.next();
            String clientSurName = scanner.next();

          Customer customer = null;
          if (StringUtils.isNotBlank(clientName) && StringUtils.isNotBlank(clientSurName)) {
                customer = new Customer(clientName, clientSurName);

            } else {
                System.out.println("Name or last name cannot be blank.");
            }

            int orderId = shop.createOrder(customer);


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

            System.out.println("payment ");
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
            System.out.println("order ID: " + orderId);

            System.out.println("How was your experience in the shop from 1-10");
            int rate = scanner.nextInt();
            Shop.clientShopRating(rate);
            scanner.close();
        }
        else if (choice == 2) {
          System.out.println("Option 2: Search for a item by name.");

            // Search for a item by name
          System.out.println("Enter item name to search: ");
            String productNameToSearch = scanner.next();
            Optional<Stock> foundProduct = productList.stream()
                    .filter(stock -> stock.getProduct().getName().equalsIgnoreCase(productNameToSearch))
                    .findFirst();
            if (foundProduct.isPresent()) {
              System.out.println("item found: " + foundProduct.get().getProduct());
            } else {
              System.out.println("item not found.");
            }
        } else if (choice == 3) {
          System.out.println("Option 3: Filter products based on a max price threshold.");

            // Filtering products with price less than a certain amount
          System.out.println("Enter price threshold: ");
            double filterPrice = scanner.nextDouble();
            List<Stock> filteredProducts = productList.stream()
                    .filter(stock -> stock.getProduct().getPrice() < filterPrice)
                    .collect(Collectors.toList());
          System.out.println("Products with price less than " + filterPrice + ": " + filteredProducts);
        }
        else if (choice == 4) {
          System.out.println("Option 4: Retrieve products within a price range.");

          System.out.println("Enter minimum price: ");
            double minPrice = scanner.nextDouble();
          System.out.println("Enter maximum price: ");
            double maxPrice = scanner.nextDouble();

            Map<String, Stock> productsInRange = productList.stream()
                    .filter(stock -> stock.getProduct().getPrice() >= minPrice && stock.getProduct().getPrice() <= maxPrice)
                    .collect(Collectors.toMap(
                            stock -> stock.getProduct().getName(),  // Key: Product name
                            stock -> stock                      // Value: Stock object
                    ));

          System.out.println("Products within price range $" + minPrice + " - $" + maxPrice + ":");
            for (Map.Entry<String, Stock> entry : productsInRange.entrySet()) {
              System.out.println("Product: " + entry.getKey() + ", Stock: " + entry.getValue());
            }
        }
        else if (choice == 5) {
          System.out.println("Option 5: Retrieve all products from a specific category.");

            // Retrieving all products from a specific category
          System.out.println("Enter category name (HOMEGOODS, BOOKS, ELECTRONICS): ");
            String categoryName = scanner.next();
            Category category = Category.valueOf(categoryName.toUpperCase());

            List<Stock> productsFromCategory = productList.stream()
                    .filter(stock -> stock.getProduct().getCategory() == category)
                    .collect(Collectors.toList());
          System.out.println("Products from " + category.getCategoryName() + ": " + productsFromCategory);
        }
        else {
            shop.showWelcomeMessage();
            shop.showProductsInShop();
            shop.showGoodbyeMessage();
          System.out.println("How was your experience in the shop from 1-10");
            int rate = scanner.nextInt();

            Shop.clientShopRating(rate);

            scanner.close();


        }
        } catch (InputMismatchException e) {
           LOGGER.error("InputMismatchException: " + e.getMessage());
           System.out.println("Invalid input. Please enter a valid choice.");
       } catch (IllegalArgumentException | NoSuchElementException e) {
   LOGGER.error("IllegalArgumentException or NoSuchElementException: " + e.getMessage());
   System.out.println("Invalid argument or element not found.");
} catch (Exception e) {
            LOGGER.error("An error occurred: " + e.getMessage(), e);
            }
    }

    }
