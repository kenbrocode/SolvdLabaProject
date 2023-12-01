package com.solvd.laba.onlineshop.Payment.Exception;

import com.solvd.laba.onlineshop.Payment.Payment;

import java.util.Date;

public class InsufficientFundsException extends Exception{

    public InsufficientFundsException(Payment payment){
        super(String.format("The account %o does not have sufficient funds to make this transaction\nPlease deposit money into your account" +
                "\nDate: %s", payment.getAccount().getCardNumber(), new Date()));
    }
}