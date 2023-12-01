package com.solvd.laba.onlineshop.Product.Exceptions;

import com.solvd.laba.onlineshop.Product.Stock;

public class OutOfStockException extends Exception{

    public OutOfStockException(String productName, Stock stock){
        super(String.format("Sorry, Unfortunately we have %d left of the product '%s'\n", stock.getQuantity(), productName));
    }

}
