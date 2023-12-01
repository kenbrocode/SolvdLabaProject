package com.solvd.laba.ecommerceshop.Payment;

public enum PaymentStatus{
    REJECTED("REJECTED"), PENDING("PENDING"), SUCCEEDED("SUCCEDED");
    String status;

    PaymentStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "PaymentStatus{" +
                "status='" + status + '\'' +
                '}';
    }
}
