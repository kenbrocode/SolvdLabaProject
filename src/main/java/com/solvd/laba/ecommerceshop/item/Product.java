package com.solvd.laba.ecommerceshop.item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class Product  {
    public static final Logger LOGGER = LogManager.getLogger(Product.class);
    private static int nextProductId = 1;
    private int Id;
    private String name;
    private int price;
    private Category category;


    public Product(String name, int price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
        Id = nextProductId++;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return String.format("Name: %s, price: %d, item ID: %d", name, price, getId());
    }

    public static Product createProduct(String name, int price, Category category) {
        try {
            Class<?> productClass = Product.class;
            Constructor<?> constructor = productClass.getConstructor(String.class, int.class, Category.class);
            return (Product) constructor.newInstance(name, price, category);
        } catch (NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;


    }
    Function<Product, String> productNameToUppercase = product -> product.getName().toUpperCase();
}
