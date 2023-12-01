package com.solvd.laba.onlineshop.Payment;

import com.solvd.laba.onlineshop.Person.Person;

public class Account{

    private long cardNumber;
    private int threeNumBackCode;
    private String expirationDate;
    private AccountType accountType;
    private int funds;

    private Person user;

    public Account(long cardNumber, int threeNumBackCode, String expirationDate, AccountType accountType, int funds){
        this.cardNumber = cardNumber;
        this.threeNumBackCode = threeNumBackCode;
        this.expirationDate = expirationDate;
        this.accountType = accountType;
        this.funds = funds;
    }


    public int getFunds(){
        return funds;
    }

    public void setFunds(int funds){
        this.funds = funds;
    }

    public void topUp(int value){
        funds += value;
    }

    public AccountType getAccountType(){
        return accountType;
    }

    public long getCardNumber(){
        return cardNumber;
    }

    public int getThreeNumBackCode(){
        return threeNumBackCode;
    }

    public String getExpirationDate(){
        return expirationDate;
    }

    public Person getUser(){
        return user;
    }

    public void setUser(Person user){
        this.user = user;
    }

    @Override
    public boolean equals(Object obj){
        return super.equals(obj);
    }
}
