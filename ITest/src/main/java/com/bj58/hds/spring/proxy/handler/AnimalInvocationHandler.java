package com.bj58.hds.spring.proxy.handler;

import com.bj58.hds.spring.interfaces.Animal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AnimalInvocationHandler implements InvocationHandler {

    private Animal target;

    public AnimalInvocationHandler(){}

    public AnimalInvocationHandler(Animal target){
        this.target = target;
    }

    public void setTarget(Animal target){
        this.target = target;
    }

    public Animal getTarget(){
        return target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy invoke "+method.getName()+" start...");
        method.invoke(target);
        System.out.println("proxy invoke "+method.getName()+" end...");
        return null;
    }
}
