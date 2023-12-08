package com.solvd.laba.ecommerceshop.item.exceptions;

import com.solvd.laba.ecommerceshop.shop.Shop;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(Shop shop, String nameOfProduct){
        super(String.format("\nThe item '%s' is not available at %s shop", nameOfProduct, shop.getName()));
    }

}
