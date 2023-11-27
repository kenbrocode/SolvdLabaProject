package com.SolvdLaba.OnlineShop.Product;

public class Stock<T extends Product>{

    private T product;
    private int quantity;

    public Stock(T product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct(){
        return product;
    }

    public void setProduct(T product){
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
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
