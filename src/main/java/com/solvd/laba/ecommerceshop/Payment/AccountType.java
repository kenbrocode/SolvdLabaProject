package com.solvd.laba.ecommerceshop.Payment;

public enum AccountType{

    VISA("Visa", "V"),
    MASTERCARD("MasterCard", "MC"),
    AMEX("American Express", "AE");

    private final String typeName;
    private final String abbreviation;

    AccountType(String typeName, String abbreviation) {
        this.typeName = typeName;
        this.abbreviation = abbreviation;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
