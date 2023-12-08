package com.solvd.laba.ecommerceshop;

import com.solvd.laba.ecommerceshop.payment.Account;
import com.solvd.laba.ecommerceshop.payment.AccountType;
import com.solvd.laba.ecommerceshop.payment.Payment;
import com.solvd.laba.ecommerceshop.payment.exceptions.NonExistentAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Set;

public class InitializeAccounts {
    public static final Logger LOGGER = LogManager.getLogger(Payment.class);

    public static Set<Account> initializeAccountsFromFile(String filePath) {
        Set<Account> accounts = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] accountInfo = line.split(",");
                if (accountInfo.length == 5) {
                    int cardNumber = Integer.parseInt(accountInfo[0]);
                    int cvv = Integer.parseInt(accountInfo[1]);
                    String expirationDate = accountInfo[2];
                    AccountType accountType = AccountType.valueOf(accountInfo[3]);
                    int funds = Integer.parseInt(accountInfo[4]);

                    Account account = new Account(cardNumber, cvv, expirationDate, accountType, funds);
                    accounts.add(account);
                } else {
                    System.out.println("Invalid account information");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        return accounts;
    }



}
