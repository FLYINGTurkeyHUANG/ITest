package com.bj58.hds.refactor.ex1.old;

import java.util.ArrayList;
import java.util.List;


/**
 * 通过对未重构的代码进行审查后可以发现如下问题
 * 1:假设用户希望输出html格式的详情单时，statement()函数无法满足要求，那么就需要重写一个htmlStatement()
 * 2:假设计价规则发生变化，那么就需要修改两个函数以保持输出结果的一致性
 * 3:假设用户希望改变影片分类规则，显然这是易变的并且会影响消费和积分点的计算方式
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
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder();
        result.append("Rental Record for"+ getName() +"\n");
        for (Rental rental: rentals) {
            double tmpAmount = 0;
            switch (rental.getMovie().getPriceCode()){
                case Movie.REGULAR:
                    tmpAmount += 2;
                    if(rental.getDaysRented() > 2){
                        tmpAmount += (rental.getDaysRented() - 2) * 1.5;
                    }
                    break;
                case Movie.NEW_RELEASE:
                    tmpAmount += rental.getDaysRented() * 3;
                    break;
                case Movie.CHILDRENS:
                    tmpAmount += 1.5;
                    if(rental.getDaysRented() > 3){
                        tmpAmount += (rental.getDaysRented() - 3) * 1.5;
                    }
                    break;
            }
            frequentRenterPoints++;
            if((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && (rental.getDaysRented() > 1)){
                frequentRenterPoints++;
            }
            result.append("\t"+rental.getMovie().getTitle()+"\t"+String.valueOf(tmpAmount)+"\n");
            totalAmount += tmpAmount;
        }

        result.append("Amount owed is "+String.valueOf(totalAmount)+"\n"+"You earned "+String.valueOf(frequentRenterPoints)+" frequest renter points");
        return result.toString();
    }

}
