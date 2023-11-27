package com.SolvdLaba.OnlineShop.Payment.Files;


import com.SolvdLaba.OnlineShop.Payment.Account;
import com.SolvdLaba.OnlineShop.Payment.AccountType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountFile{
    private static final String pattern = "([0-9]{5})\s([0-9]{3})\s([0-9]{2}/[0-9]{2})\s([A-Z]+)\s([0-9]+)";
    static Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

    public static Set<Account> parse(File file){
        Set<Account> accounts = new HashSet<>();
        try{
            String line;
            BufferedReader read = new BufferedReader(new FileReader(file));

            while ((line = read.readLine()) != null){
                Matcher matcher = pat.matcher(line);
                if (matcher.find()){
                    String[] wholeLine = line.split(" ");
                    long cardNumber = Long.parseLong(wholeLine[0]);
                    int threeNumBackCode = Integer.parseInt(wholeLine[1]);
                    String expirationDate = wholeLine[2];
                    AccountType accountType = AccountType.valueOf(wholeLine[3]);
                    int funds = Integer.parseInt(wholeLine[4]);
                    Account account = new Account(cardNumber, threeNumBackCode, expirationDate, accountType, funds);
                    accounts.add(account);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return accounts;
    }
}
