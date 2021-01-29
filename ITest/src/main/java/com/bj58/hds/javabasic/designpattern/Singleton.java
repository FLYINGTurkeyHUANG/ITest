package com.bj58.hds.javabasic.designpattern;


/**
 * 单例模式
 * */
public class Singleton {

    private static volatile Singleton instance;
//    public static int flag = 1;
    private Singleton(){}

    public static Singleton getInstance() throws InterruptedException {
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;

/**
 * Main方法打印如下
 * Thread-1com.bj58.hds.javabasic.designpattern.Singleton@14f2bcd0
 * Thread-0com.bj58.hds.javabasic.designpattern.Singleton@5da9d59
 * 通过flag控制先获取到锁的线程sleep,模拟线程1同步块的线程后sleep等待创建新对象，而线程2已经创建对象并返回，待线程1恢复又创建新的对象并返回，使得此实现不安全。
 * if(instance == null){
 *     synchronized (Singleton.class){
 *         if(flag == 1){//先进入的线程sleep
 *            flag = 0;
 *            Thread.currentThread().sleep(1000);
 *             instance = new Singleton();
 *         }else if(flag == 0){//后进入的线程直接new并返回
 *             instance = new Singleton();
 *        }
 *     }
 * }
 * return instance;
 */
    }

}
