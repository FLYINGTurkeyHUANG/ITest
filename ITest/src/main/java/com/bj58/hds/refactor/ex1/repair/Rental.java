package com.bj58.hds.refactor.ex1.repair;

public class Rental {

    //  电影
    private Movie movie;

    //  租借时间
    private int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }

    public double getCharge() {
        //由于计算的switch结构使用到了movie的内部属性，应该放在Movie中
        return movie.getCharge(daysRented);
    }

    public int getFrequentRenterPoints(){
        return movie.getFrequentRenterPoints(daysRented);
    }
}
