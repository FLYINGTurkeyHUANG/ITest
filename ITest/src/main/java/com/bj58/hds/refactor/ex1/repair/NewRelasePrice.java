package com.bj58.hds.refactor.ex1.repair;

public class NewRelasePrice extends Price{
    @Override
    public int getPriceCode() {
        return Movie.NEW_RELEASE;
    }

    @Override
    public double getCharge(int daysRented) {
        return daysRented * 3;
    }

    public int getFrequentRenterPoints(int daysRented){
        return (daysRented > 1) ? 2 : 1;
    }
}
