package com.solvd.laba.ecommerceshop.payment;


import com.solvd.laba.ecommerceshop.order.Order;
import com.solvd.laba.ecommerceshop.order.OrderStatus;
import com.solvd.laba.ecommerceshop.payment.exceptions.InsufficientFundsException;
import com.solvd.laba.ecommerceshop.payment.exceptions.NonExistentAccount;
import com.solvd.laba.ecommerceshop.person.Person;
import com.solvd.laba.ecommerceshop.utils.InitializeAccounts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;


public class Payment {
    public static final Logger LOGGER = LogManager.getLogger(Payment.class);

    private Account account;
    private Person sender;
    private int amount;
    private Order order;
    private String address;
    private PaymentStatus paymentStatus;
    private int funds;

    public Payment(Account account, Person sender, Order receipt, String address){
        this.account = account;
        this.sender = sender;
        this.order = receipt;
        this.amount = receipt.getTotal();
        this.address = address;
        this.funds = account.getFunds();
    }
    static String accountsFilePath = "src/main/resources/accounts";
    static Set<Account> accounts = InitializeAccounts.initializeAccountsFromFile(accountsFilePath);
    public static boolean validateAccount(Account account) {
        LOGGER.info("Validating your account");

        // Ensure the 'accounts' set is properly initialized with accounts data from the file
        if (accounts == null || accounts.isEmpty()) {
            // Handle uninitialized or empty accounts set
            return false;
        }

        boolean isValid = accounts.stream()
                .anyMatch(account1 -> account1.getCardNumber() == account.getCardNumber());

        if (!isValid) {
            try {
                throw new NonExistentAccount(account);
            } catch (NonExistentAccount e) {
                e.printStackTrace();
            }
        }

        return isValid;
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
