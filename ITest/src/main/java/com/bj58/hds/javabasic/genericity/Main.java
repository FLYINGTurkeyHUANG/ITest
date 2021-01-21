package com.bj58.hds.javabasic.genericity;


public class Main {
    public static void main(String[] args) {
        Pair<Integer> pair = new Pair<>(1,2);
        Integer low = pair.getLow();
        System.out.println(low);
    }
}
