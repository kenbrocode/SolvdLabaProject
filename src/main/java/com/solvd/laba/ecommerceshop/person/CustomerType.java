package com.solvd.laba.ecommerceshop.person;

public enum CustomerType{
    BASIC(0, "Basic Customer"),
    INTERMEDIATE(200, "Intermediate Customer"),
    PRO(500, "Pro Customer");

    private final int leastAmountSpent;
    private final String description;

    CustomerType(int leastAmountSpent, String description) {
        this.leastAmountSpent = leastAmountSpent;
        this.description = description;
    }

    public int getLeastAmountSpent() {
        return leastAmountSpent;
    }

    public String getDescription() {
        return description;
    }

    // Additional method to get a formatted string of enum details
    public String getDetails() {
        return String.format("Type: %s, Least Amount Spent: %d", description, leastAmountSpent);
    }

    // Additional method to determine if a customer type is special based on the least amount spent
    public boolean isSpecialType() {
        return leastAmountSpent > 0;
    }
}
