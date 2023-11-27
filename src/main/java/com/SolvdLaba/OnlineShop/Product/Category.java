package com.SolvdLaba.OnlineShop.Product;

public enum Category{
    HOUSE("HOUSE"), BOOKS("BOOKS"), ELECTRONICS("ELECTRONICS");

    String attributedValue;

    Category(String attributedValue){
        this.attributedValue = attributedValue;
    }

    public String getAttributedValue(){
        return attributedValue;
    }
}
