package com.bj58.hds.refactor.ex1.repair;

public class ChildrensPrice extends Price{

    @Override
    int getPriceCode() {
        return Movie.CHILDRENS;
    }

    @Override
    double getCharge(int daysRented) {
        double result = 1.5;
        if(daysRented > 3){
            result += (daysRented - 3) * 1.5;
        }
        return result;
    }
}
