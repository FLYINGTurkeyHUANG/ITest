package com.bj58.hds.refactor.ex1.repair;

public abstract class Price {
    abstract int getPriceCode();
    abstract double getCharge(int daysRented);
    int getFrequentRenterPoints(int daysRented){ return 1;}
}
