package com.SolvdLaba.OnlineShop.Product;

import java.util.ArrayList;
import java.util.List;

public class Product implements Comparable<Product>{
    private static int nextProductId = 1;
    private final int productId;
    private final String name;
    private final int price;
    private final Category category;
    private static final List<Stock<Product>> productList = new ArrayList<>();

    static {
        initializeProducts();
    }

    private static void initializeProducts() {
        Product chair = new Product("Chair", 50, Category.HOUSE);
        Product desk = new Product("Desk", 50, Category.HOUSE);
        Product pans = new Product("Pans", 50, Category.HOUSE);
        Product heater = new Product("Heater", 50, Category.HOUSE);

        Product historyBook = new Product("Historybook", 100, Category.BOOKS);
        Product programmingBook = new Product("Programmingbook", 100, Category.BOOKS);
        Product niceBook = new Product("Nicebook", 100, Category.BOOKS);
        Product bookBook = new Product("Bookbook", 100, Category.BOOKS);

        productList.add(new Stock<>(chair, 50));
        productList.add(new Stock<>(desk, 50));
        productList.add(new Stock<>(pans, 50));
        productList.add(new Stock<>(heater, 50));
        productList.add(new Stock<>(historyBook, 50));
        productList.add(new Stock<>(programmingBook, 50));
        productList.add(new Stock<>(niceBook, 50));
        productList.add(new Stock<>(bookBook, 50));
    }


    public Product(String name, int price, Category category){
        this.name = name;
        this.price = price;
        this.category = category;
        productId = nextProductId++;
    }

    public int getProductId(){
        return productId;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public Category getCategory(){
        return category;
    }


    @Override
    public int hashCode(){
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        return super.equals(obj);
    }

    @Override
    public String toString(){
        return String.format("Name: %s, price: %d, codeNumber: %d", name, price, getProductId());
    }

    @Override
    public int compareTo(Product o){
        return this.category.compareTo(o.category);
    }
}
