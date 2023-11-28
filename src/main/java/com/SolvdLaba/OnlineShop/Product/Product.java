package com.SolvdLaba.OnlineShop.Product;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Product implements Comparable<Product>{
    private static int nextProductId = 1;
    private final int productId;
    private String name;
    private final int price;
    private final Category category;
    private static final List<Stock<Product>> productList = new ArrayList<>();




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

    public void manipulateProductName() {
        // String manipulation using StringUtils
        name = StringUtils.capitalize(name);
        name = StringUtils.reverse(name);
        System.out.println("Modified Product Name: " + name);
    }
}
