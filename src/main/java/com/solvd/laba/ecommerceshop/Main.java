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

    private static Map<String, String> clientNames = new HashMap<>();

    interface ProductValueCalculator {
        double calculateValue(Stock stock);
    }

    public static void main(String[] args) {

            String accountsFilePath = "src/main/resources/accounts";
            InitializeAccounts.initializeAccountsFromFile(accountsFilePath);


            List<Stock> productList = new ArrayList<>();

            // Using reflection to create instances of products and add them to the productList
            productList.add(new Stock(Product.createProduct("Chair", 50, Category.HOMEGOODS), 50));
            productList.add(new Stock(Product.createProduct("Desk", 100, Category.HOMEGOODS), 30));
            productList.add(new Stock(Product.createProduct("lamp", 25, Category.HOMEGOODS), 20));
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
                System.out.println("6. Press 6 to manipulate product list ");
                System.out.println("7. Press 7 to sort products by price ");
                System.out.println("8. Press 8 to filter products by name length ");
                System.out.println("9. Press 9 to count total quantity of a specific product ");
                System.out.println("10. Press 10 to calculate the total value of a product in stock");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.println("Insert your name and last name");
                    String clientName = scanner.next();
                    String clientSurName = scanner.next();


                    Customer customer = null;
                    if (StringUtils.isNotBlank(clientName) && StringUtils.isNotBlank(clientSurName)) {
                        clientNames.put(clientName, clientSurName); // Storing client name and surname in the map
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
                    Shipment.shipping(courier, shipment);
                    System.out.println("order ID: " + orderId);

                    System.out.println("How was your experience in the shop from 1-10");
                    int rate = scanner.nextInt();
                    Shop.clientShopRating(rate);
                    scanner.close();
                } else if (choice == 2) {
                    System.out.println("Option 2: Search for a item by name.");

                    // Search for a item by name
                    System.out.println("Enter item name to search: ");
                    String productNameToSearch = scanner.next();

                    Optional<Stock> foundProduct = productList.stream()
                            .filter(stock -> StringUtils.equalsIgnoreCase(stock.getProduct().getName(), productNameToSearch))
                            .findFirst();

                    if (foundProduct.isPresent()) {
                        System.out.println("Item found: " + foundProduct.get().getProduct());
                    } else {
                        System.out.println("Item not found.");
                    }
                } else if (choice == 3) {
                    System.out.println("Enter price threshold: ");
                    double filterPrice = scanner.nextDouble();

                    productList.stream()
                            .filter(stock -> stock.getProduct().getPrice() < filterPrice)
                            .forEach(stock -> {
                                System.out.println("Product: " + stock.getProduct().getName());
                                System.out.println("Product Information: " + stock.getProduct().toString());
                                String category = Optional.ofNullable(stock.getProduct().getCategory())
                                        .map(Category::getCategoryName)
                                        .orElse("No Category Found");
                                System.out.println("Category: " + category);


                            });
                } else if (choice == 4) {
                    System.out.println("Option 4: Retrieve products within a price range.");
                    System.out.println("Enter minimum price: ");
                    double minPrice = scanner.nextDouble();
                    System.out.println("Enter maximum price: ");
                    double maxPrice = scanner.nextDouble();

                    Map<String, Stock> productsInRange = productList.stream()
                            .filter(stock -> stock.getProduct().getPrice() >= minPrice && stock.getProduct().getPrice() <= maxPrice)
                            .collect(Collectors.toMap(
                                    stock -> stock.getProduct().getName(),
                                    stock -> stock
                            ));

                    productsInRange.forEach((productName, stock) -> System.out.println("Product: " + productName + ", Stock: " + stock));
                } else if (choice == 5) {
                    System.out.println("Option 5: Retrieve all products from a specific category.");

                    // Retrieving all products from a specific category
                    System.out.println("Enter category name (HOMEGOODS, BOOKS, ELECTRONICS): ");
                    String categoryName = scanner.next();
                    Category category = Category.valueOf(categoryName.toUpperCase());

                    List<Stock> productsFromCategory = productList.stream()
                            .filter(stock -> stock.getProduct().getCategory() == category)
                            .collect(Collectors.toList());
                    System.out.println("Products from " + category.getCategoryName() + ": " + productsFromCategory);
                } else if (choice == 6) {
                    System.out.println("Option 6: Manipulate product list");
                    System.out.println("1. Press 1 to reverse the product names");
                    System.out.println("2. Press 2 to capitalize all product names");
                    System.out.println("3. Press 3 to convert all product names to lowercase");
                    System.out.println("4. Press 4 to trim all product names");
                    int operationChoice = scanner.nextInt();

                    switch (operationChoice) {
                        case 1:
                            productList.forEach(stock -> stock.getProduct().setName(StringUtils.reverse(stock.getProduct().getName())));
                            break;
                        case 2:
                            productList.forEach(stock -> stock.getProduct().setName(StringUtils.capitalize(stock.getProduct().getName())));
                            break;
                        case 3:
                            productList.forEach(stock -> stock.getProduct().setName(StringUtils.lowerCase(stock.getProduct().getName())));
                            break;
                        case 4:
                            productList.forEach(stock -> stock.getProduct().setName(StringUtils.trim(stock.getProduct().getName())));
                            break;
                        default:
                            System.out.println("Invalid operation choice.");
                            break;
                    }

                    System.out.println("Updated Product List:");
                    productList.forEach(stock -> System.out.println(stock.getProduct()));
                } else if (choice == 7) {
                    System.out.println("Option 7: Sort products by price");
                    System.out.println("1. Press 1 to sort by price in ascending order");
                    System.out.println("2. Press 2 to sort by price in descending order");
                    int sortChoice = scanner.nextInt();

                    switch (sortChoice) {
                        case 1:
                            Collections.sort(productList, Comparator.comparingDouble(stock -> stock.getProduct().getPrice()));
                            break;
                        case 2:
                            Collections.sort(productList, (stock1, stock2) ->
                                    Double.compare(stock2.getProduct().getPrice(), stock1.getProduct().getPrice()));
                            break;
                        default:
                            System.out.println("Invalid sort choice.");
                            break;
                    }

                    // Display sorted products
                    productList.forEach(stock -> System.out.println("Product: " + stock.getProduct().getName() + ", Price: " + stock.getProduct().getPrice()));
                } else if (choice == 8) {
                    System.out.println("Option 8: Filter products by name length");
                    System.out.println("Enter name length to filter: ");
                    int nameLength = scanner.nextInt();

                    List<Stock> filteredProducts = productList.stream()
                            .filter(stock -> stock.getProduct().getName().length() == nameLength)
                            .collect(Collectors.toList());

                    System.out.println("Products with name length " + nameLength + ":");
                    filteredProducts.forEach(stock -> System.out.println("Product: " + stock.getProduct().getName()));
                } else if (choice == 9) {
                    System.out.println("Option 9: Count total quantity of a specific product");

                    System.out.println("Enter the name of the product to count its total quantity: ");
                    String productNameToCount = scanner.next();

                    long totalQuantity = productList.stream()
                            .filter(stock -> stock.getProduct().getName().equalsIgnoreCase(productNameToCount))
                            .mapToLong(Stock::getQuantity)
                            .sum();

                    System.out.println("Total quantity of " + productNameToCount + " in stock: " + totalQuantity);
                }
                else if (choice == 10) {
                    System.out.println("Option 10: Calculate the total value of a product in stock");

                    System.out.println("Enter the name of the product:");
                    String productName = scanner.next();

                    Optional<Stock> selectedProduct = productList.stream()
                            .filter(stock -> stock.getProduct().getName().equalsIgnoreCase(productName))
                            .findFirst();

                    if (selectedProduct.isPresent()) {
                        ProductValueCalculator valueCalculator = stock -> stock.getProduct().getPrice() * stock.getQuantity();
                        double totalValue = valueCalculator.calculateValue(selectedProduct.get());
                        System.out.println("Total value of " + productName + " in stock: " + totalValue);
                    } else {
                        System.out.println("Product not found in stock.");
                    }
                }

                else {
                    Runnable runnable = () -> System.out.println("This code is running in a separate thread.");
                    Thread thread = new Thread(runnable);
                    thread.start();
                    shop.showWelcomeMessage();
                    shop.showProductsInShop();
                    shop.showGoodbyeMessage();
                    System.out.println("How was your experience in the shop from 1-10");
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

