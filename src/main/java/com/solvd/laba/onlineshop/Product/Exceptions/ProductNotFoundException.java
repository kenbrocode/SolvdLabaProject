package com.solvd.laba.onlineshop.Product.Exceptions;

import com.solvd.laba.onlineshop.Shop.Shop;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(Shop shop, String nameOfProduct){
        super(String.format("\nThe product '%s' is not available at %s shop", nameOfProduct, shop.getName()));
    }

}
