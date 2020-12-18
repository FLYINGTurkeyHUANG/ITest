package com.bj58.hds.javabasic.deadlock;


/**
 * 运行后产生死锁，通过jsp -l 获取当前进程的pid，再通过jstack pid就可以进行死锁分析。
 * Found one Java-level deadlock：之后的信息会指出产生死锁的位置。
 * */
public class Main {
    public static void main(String[] args) {
        final Object a = new Object();
        final Object b = new Object();
        new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (a) {
                        System.out.println("T1 got the lock of a");
                        Thread.sleep(1000);
                        System.out.println("T1 was trying to get the lock of b");
                        synchronized (b) {
                            System.out.println("T1 one win");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (b) {
                        System.out.println("T2 two got the lock of b");
                        Thread.sleep(1000);
                        System.out.println("T2 was trying to get the lock of a");
                        synchronized (a) {
                            System.out.println("T2 win");

                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }

}
