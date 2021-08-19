package com.bj58.hds.refactor.ex1.repair;

import java.util.ArrayList;
import java.util.List;


/**
 * 重构后
 * */
public class Customer {

    //  顾客姓名
    private String name;

    //  租借信息
    private List<Rental> rentals = new ArrayList<>();

    public Customer(String name, List<Rental> rentals) {
        this.name = name;
        this.rentals = rentals;
    }

    public String getName() {
        return name;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public void addRental(Rental rental){
        rentals.add(rental);
    }

    public String statement(){
        double totalAmount = 0;
        StringBuilder result = new StringBuilder();
        result.append("Rental Record for"+ getName() +"\n");
        for (Rental rental: rentals) {
            result.append("\t"+rental.getMovie().getTitle()+"\t"+String.valueOf(rental.getCharge())+"\n");
        }

        result.append("Amount owed is "+String.valueOf(getTotalCharge())+"\n")
                .append("You earned "+String.valueOf(getTotalFrequentRenterPoints())+" frequest renter points");
        return result.toString();
    }

    public String htmlStatement(){
        // todo 输出html的详情单
        return "";
    }

    //  将计价逻辑单独提出作为一个方法，实际的计算逻辑放在租借信息的类当中
    private double getTotalCharge() {
        double result = 0;
        for (Rental rental: rentals) {
            result += rental.getCharge();
        }
        return result;
    }

    //  将积分计算提出作为一个方法放在租借信息的类中
    private int getTotalFrequentRenterPoints() {
        int result = 0;
        for (Rental rental: rentals) {
            result += rental.getFrequentRenterPoints();
        }
        return result;
    }



}

