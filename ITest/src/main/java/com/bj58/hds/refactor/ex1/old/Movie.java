package com.bj58.hds.refactor.ex1.old;

public class Movie {

    //  常规电影
    public final static int REGULAR = 0;

    //  新上电影
    public final static int NEW_RELEASE = 1;

    //  儿童电影
    public final static int CHILDRENS = 2;

    //  电影名
    private String title;

    //  价格类
    private int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriceCode(int priceCode) { this.priceCode = priceCode;}

}
