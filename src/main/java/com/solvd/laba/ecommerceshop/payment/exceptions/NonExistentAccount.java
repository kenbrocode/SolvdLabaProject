package com.solvd.laba.ecommerceshop.payment.exceptions;

import com.solvd.laba.ecommerceshop.payment.Account;

public class NonExistentAccount extends Exception{

    public NonExistentAccount(Account account){
        super(String.format("The account %s you are trying to use, doesnt exist", account.getCardNumber()));
    }
}
