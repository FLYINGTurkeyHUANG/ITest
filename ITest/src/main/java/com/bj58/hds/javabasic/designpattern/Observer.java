package com.bj58.hds.javabasic.designpattern;


import java.util.Random;

/**
 * 观察者模式
 * 监听操作可以通过Listener完成监听回调，实现被观察者发生变化时观察者执行特定逻辑
 * */
public class Observer {

    public void observe(String name,Object value) throws Exception {
        switch(name){
            case "weight":
                observeWeight((int) value);
                return ;
            case "hungary":
                observeHungary((boolean) value);
                return ;
            case "tired":
                observeTired((boolean) value);
                return ;
            default:
                throw new Exception("不存在观察对应属性的处理方法");
        }
    }

    public void observeWeight(int weight){
        System.out.println("观察Panda的体重并打印，体重="+weight+"kg,"+(weight>150?"太胖了，运动减肥":"正常"));
    }

    public void observeHungary(boolean isHungary){
        System.out.println("观察Panda的饥饿状态并打印，饥饿="+isHungary+(isHungary?",并投喂":",正常"));
    }

    public void observeTired(boolean isTired){
        System.out.println("观察Panda的疲劳状态并打印，疲劳="+isTired+(isTired?",并关灯睡觉":",正常"));
    }


    public static void main(String[] args) throws InterruptedException {
        Observer weightObserver = new Observer();
        Observer hungaryObserver = new Observer();
        Observer tiredObserver = new Observer();
        Panda panda = new Panda(100,false,false);
        panda.register("weight",weightObserver);
        panda.register("hungary",weightObserver);
        panda.register("tired",weightObserver);
        Random random = new Random();
        for(;;){
            panda.setWeight(random.nextInt(200));
            panda.setIsHungary(random.nextBoolean());
            panda.setIsTired(random.nextBoolean());
            Thread.sleep(1000);
        }
    }
}
