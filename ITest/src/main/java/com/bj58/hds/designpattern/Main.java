package com.bj58.hds.designpattern;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        for(int i=0;i<2;i++){
            new Thread(){
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        System.out.println(Thread.currentThread().getName()+Singleton.getInstance());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            countDownLatch.countDown();
        }

    }
}
