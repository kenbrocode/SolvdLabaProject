package com.SolvdLaba.OnlineShop.Product.Exceptions;

import com.SolvdLaba.OnlineShop.Product.Stock;

public class OutOfStockException extends Exception{

    public OutOfStockException(String productName, Stock stock){
        super(String.format("Sorry, Unfortunately we have %d left of the product '%s'\n", stock.getQuantity(), productName));
    }

}
