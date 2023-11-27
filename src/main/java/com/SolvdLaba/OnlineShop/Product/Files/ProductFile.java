package com.SolvdLaba.OnlineShop.Product.Files;

import com.SolvdLaba.OnlineShop.Product.Category;
import com.SolvdLaba.OnlineShop.Product.Product;
import com.SolvdLaba.OnlineShop.Product.Stock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductFile{

    private static final String pattern = "([A-Z]{1}[a-z]+)\s([0-9]+)\s([A-Z]+)\s([0-9]+)";
    static Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

    public static List<Stock> parse(File file){
        List<Stock> stocks = new ArrayList<>();
        try{
            String line;
            BufferedReader read = new BufferedReader(new FileReader(file));

            while ((line = read.readLine()) != null){
                Matcher matcher = pat.matcher(line);
                if (matcher.find()){
                    String name;
                    int price;
                    Category category;
                    int quantity;
                    String[] wholeLine = line.split(" ");
                    name = wholeLine[0];
                    price = Integer.parseInt(wholeLine[1]);
                    category = Category.valueOf(wholeLine[2]);
                    quantity = Integer.parseInt(wholeLine[3]);
                    Product product = new Product(name, price, category);
                    Stock stock = new Stock<>(product, quantity);
                    stocks.add(stock);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return stocks;
    }
}
