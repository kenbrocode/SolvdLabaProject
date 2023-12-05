package com.solvd.laba.ecommerceshop.Payment;


import com.solvd.laba.ecommerceshop.Order.Order;
import com.solvd.laba.ecommerceshop.Order.OrderStatus;
import com.solvd.laba.ecommerceshop.Payment.Exception.InsufficientFundsException;
import com.solvd.laba.ecommerceshop.Payment.Exception.NonExistentAccount;
import com.solvd.laba.ecommerceshop.Person.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;


public class Payment {
    public static final Logger LOGGER = LogManager.getLogger(Payment.class);
    static Set<Account> accounts = initializeAccounts();
    private Account account;
    private Person sender;
    private int amount;
    private Order order;
    private String address;
    private PaymentStatus paymentStatus;
    private int funds;
    private static Set<Account> initializeAccounts() {
        Set<Account> accounts = new HashSet<>();

        Account account1 = new Account(12345678, 143, "11/26", AccountType.VISA, 109999);
        Account account2 = new Account(67891456, 648, "10/25", AccountType.MASTERCARD, 9999);
        Account account3 = new Account(91234567, 112, "11/27", AccountType.MASTERCARD, 10000);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        return accounts;
    }
    public Payment(Account account, Person sender, Order receipt, String address){
        this.account = account;
        this.sender = sender;
        this.order = receipt;
        this.amount = receipt.getTotal();
        this.address = address;
        this.funds = account.getFunds();
    }

    public static boolean validateAccount(Account account){
        LOGGER.info("Validating your account");
        for (Account account1 : accounts){
            if (account1.getCardNumber() == account.getCardNumber()){
                return true;
            }
        }
        try{
            throw new NonExistentAccount(account);
        } catch (NonExistentAccount e){
            e.printStackTrace();
            return false;
        }
    }

    public Account getAccount(){
        return account;
    }

    public int getFunds(){
        return funds;
    }



    public boolean pay(int value){
        if (funds - value >= 0){
            funds -= value;
            paymentStatus = PaymentStatus.SUCCEEDED;
            order.setOrderStatus(OrderStatus.CREATED);
        } else{
            paymentStatus = PaymentStatus.REJECTED;
            try{
                throw new InsufficientFundsException(this);
            } catch (InsufficientFundsException e){
                e.printStackTrace();
                return false;
            }

        }
        return true;
    }

    public Person getSender(){
        return sender;
    }

    public PaymentStatus getPaymentStatus(){
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus){
        this.paymentStatus = paymentStatus;
    }

    public Order getReceipt(){
        return order;
    }

    public int getAmount(){
        return amount;
    }

    public String getAddress(){
        return address;
    }

}
