package com.bj58.hds.javabasic.designpattern;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        for(int i=0;i<10000;i++){
            new Thread(){
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        System.out.println(Singleton.getInstance());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            countDownLatch.countDown();
        }

    }
}
