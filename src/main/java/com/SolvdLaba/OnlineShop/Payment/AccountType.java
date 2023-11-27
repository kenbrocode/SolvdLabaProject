package com.SolvdLaba.OnlineShop.Payment;

public enum AccountType{

    VISA("VISA"), MASTERCARD("MASTERCARD"), EXPRESS("EXPRESS");

    private final String accountType;

    AccountType(String accountType){
        this.accountType = accountType;
    }

    public String getAccountType(){
        return accountType;
    }
}
