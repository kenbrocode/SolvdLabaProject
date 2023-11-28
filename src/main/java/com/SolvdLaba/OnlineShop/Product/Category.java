package com.SolvdLaba.OnlineShop.Product;

public enum Category{
    HOMEGOODS("HOMEGOODS"), BOOKS("BOOKS"), ELECTRONICS("ELECTRONICS");

    String attributedValue;

    Category(String attributedValue){
        this.attributedValue = attributedValue;
    }

    public String getAttributedValue(){
        return attributedValue;
    }
}
