package com.bj58.hds.javabasic.designpattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Panda{

    public void setObserverMap(Map<String, List<Observer>> observerMap) {
        this.observerMap = observerMap;
    }

    private Map<String, List<Observer>> observerMap;

    private int weight;

    private boolean isHungary;

    private boolean isTired;

    public Panda(int weight,boolean isHungary,boolean isTired){
        observerMap = new HashMap<>();
        this.weight = weight;
        this.isHungary = isHungary;
        this.isTired = isTired;
    }

    /**
     * 注册观察者，以便在对象发生变化时进行通知
     * */
    public void register(String name,Observer observer){
        if(observerMap.get(name) != null){
            observerMap.get(name).add(observer);
        }else{
            List<Observer> observers = new ArrayList<>();
            observers.add(observer);
            observerMap.put(name,observers);
        }
    }

    /**
     * 通知体重观察者
     * */
    public void notifyObservers(String name,Object value){
        List<Observer> observers = observerMap.get(name);
        for(Observer observer:observers){
            try {
                observer.observe(name,value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, List<Observer>> getObserverMap() {
        return observerMap;
    }

    public int getWeight() {
        return weight;
    }

    public boolean getIsHungary() {
        return isHungary;
    }

    public boolean getIsTired() {
        return isTired;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        notifyObservers("weight",weight);
    }

    public void setIsHungary(boolean isHungary) {
        this.isHungary = isHungary;
        notifyObservers("hungary",isHungary);
    }

    public void setIsTired(boolean isTired) {
        this.isTired = isTired;
        notifyObservers("tired",isTired);
    }

}
