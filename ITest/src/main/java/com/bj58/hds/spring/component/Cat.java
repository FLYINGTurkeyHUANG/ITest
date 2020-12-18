package com.bj58.hds.spring.component;

import com.bj58.hds.spring.interfaces.Animal;

public class Cat implements Animal {

    @Override
    public void move() {
        System.out.println("cat is moving!");
    }

    @Override
    public void sleep() {
        System.out.println("cat is sleeping");
    }

    @Override
    public void play() {
        System.out.println("cat is palying");
    }
}
