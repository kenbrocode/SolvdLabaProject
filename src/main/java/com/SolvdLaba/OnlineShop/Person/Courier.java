package com.SolvdLaba.OnlineShop.Person;

import com.SolvdLaba.OnlineShop.Shipment.Shipment;

import java.util.LinkedList;
import java.util.List;

public class Courier extends Person{


    private final String contactNum;
    private List<Shipment> shipments;

    public Courier(String name, String surname, String contactNum){
        super(name, surname);
        this.contactNum = contactNum;
        shipments = new LinkedList<>();
    }

    @Override
    public String getName(){
        return super.getName();
    }

    @Override
    public String getSurname(){
        return super.getSurname();
    }

    @Override
    public String toString(){
        return super.toString();
    }

    public List<Shipment> getShipment(){
        return shipments;
    }

    public void setShipment(List<Shipment> shipment){
        this.shipments = shipment;
    }

    public void addShipment(Shipment shipment){
        shipments.add(shipment);
    }

    public void removeShipment(Shipment shipment){
        shipments.remove(shipment);
    }

    public String getContactNum(){
        return contactNum;
    }

}
