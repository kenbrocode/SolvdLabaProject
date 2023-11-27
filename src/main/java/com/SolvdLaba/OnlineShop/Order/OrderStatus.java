package com.SolvdLaba.OnlineShop.Order;

public enum OrderStatus{
    CREATED("CREATED"), WAITING_FOR_PAYMENT("WAITING FOR PAYMENT"), CONFIRMED("CONFIRMED"), DECLINED("DECLINED"), CANCELLED("CANCELLED");
    String status;

    OrderStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "OrderStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
