package com.bj58.hds.refactor.ex1.repair;

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
    private Price price;

    public Movie(String title, Price price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public int getPriceCode() {
        return price.getPriceCode();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriceCode(int priceCode) {
        switch (priceCode){
            case REGULAR:
                price = new RegularPrice();
                break;
            case CHILDRENS:
                price = new ChildrensPrice();
                break;
            case NEW_RELEASE:
                price = new NewRelasePrice();
                break;
            default:
                throw new IllegalArgumentException("Incorrect Price Code");
        }

    }

    //通过多态去替代switch等结构
    public double getCharge(int daysRented) {
        return price.getCharge(daysRented);
    }

    public int getFrequentRenterPoints(int daysRented){
        return price.getFrequentRenterPoints(daysRented);
    }

}
