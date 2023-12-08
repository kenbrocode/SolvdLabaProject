package com.solvd.laba.ecommerceshop.item;

public class Stock {

    private Product product;
    private int quantity;

    public Stock(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int totalPerProduct(){
        return quantity * product.getPrice();
    }

    @Override
    public String toString(){
        return "Stock{" +
                "item=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
