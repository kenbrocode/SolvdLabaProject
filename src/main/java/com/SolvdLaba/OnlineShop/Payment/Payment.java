package com.SolvdLaba.OnlineShop.Payment;


import com.SolvdLaba.OnlineShop.Order.Order;
import com.SolvdLaba.OnlineShop.Order.OrderStatus;
import com.SolvdLaba.OnlineShop.Payment.Exception.InsufficientFundsException;
import com.SolvdLaba.OnlineShop.Payment.Exception.NonExistentAccount;
import com.SolvdLaba.OnlineShop.Person.Person;
import com.SolvdLaba.OnlineShop.Payment.Files.AccountFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Set;


public class Payment {
    public static final Logger LOGGER = LogManager.getLogger(Payment.class);
    private static final Set<Account> accounts = AccountFile.parse(Path.of("src/main/java/com/SolvdLaba/OnlineShop/Payment/Files/Accounts").toFile());
    private final Account account;
    private final Person sender;
    private final int amount;
    private final Order order;
    private final String address;
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
            order.setOrderStatus(OrderStatus.CONFIRMED);
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
