package com.bj58.hds.spring.component;

import com.bj58.hds.spring.interfaces.Animal;

public class Dog implements Animal {
    @Override
    public void move() {
        System.out.println("dog is moving!");
    }

    @Override
    public void sleep() {
        System.out.println("dog is sleeping");
    }

    @Override
    public void play() {
        System.out.println("dog is palying");
    }
}
