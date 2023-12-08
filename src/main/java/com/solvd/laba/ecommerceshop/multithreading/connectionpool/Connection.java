package com.solvd.laba.ecommerceshop.multithreading.connectionpool;

public class Connection {
    private static int counter = 0;
    private int id;

    public Connection() {
        this.id = ++counter;
    }

    public static synchronized int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "Connection " + id;
    }
}