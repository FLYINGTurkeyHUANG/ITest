package com.bj58.hds.spring.component;

import com.bj58.hds.spring.interfaces.Animal;

public class Fish implements Animal {
    @Override
    public void move() {
        System.out.println("fish is moving!");
    }

    @Override
    public void sleep() {
        System.out.println("fish is sleeping");
    }

    @Override
    public void play() {
        System.out.println("fish is palying");
    }
}
