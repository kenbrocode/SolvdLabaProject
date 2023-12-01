package com.SolvdLaba.OnlineShop.Product;

import com.SolvdLaba.OnlineShop.Order.Order;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Product {
    public static final Logger LOGGER = LogManager.getLogger(Product.class);
    private static int nextProductId = 1;
    private int productId;
    private String name;
    private int price;
    private Category category;
    private static List<Stock<Product>> productList = new ArrayList<>();




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


    public void manipulateProductName() {
        // String manipulation using StringUtils
        name = StringUtils.capitalize(name);
        name = StringUtils.reverse(name);
        LOGGER.info("Modified Product Name: " + name);
    }
}
