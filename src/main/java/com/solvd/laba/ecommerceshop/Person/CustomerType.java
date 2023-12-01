package com.solvd.laba.ecommerceshop.Person;

public enum CustomerType{
    BASIC(0), INTERMEDIATE(200), PRO(500);
    private final int leastAmountSpent;

    CustomerType(int leastAmountSpent){
        this.leastAmountSpent = leastAmountSpent;
    }
}
