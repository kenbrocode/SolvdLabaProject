package com.solvd.laba.ecommerceshop.item.exceptions;

import com.solvd.laba.ecommerceshop.item.Stock;

public class OutOfStockException extends Exception{

    public OutOfStockException(String productName, Stock stock){
        super(String.format("Sorry, Unfortunately we have %d left of the item '%s'\n", stock.getQuantity(), productName));
    }

}
