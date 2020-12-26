package com.bj58.hds.spring.proxy;


import com.bj58.hds.spring.component.Cat;
import com.bj58.hds.spring.interfaces.Animal;
import com.bj58.hds.spring.proxy.factory.JDKProxyFactory;
import com.bj58.hds.spring.proxy.handler.AnimalInvocationHandler;

/**
 * jdk代理示例
 * */
public class JDKProxyTest {

    public static void main(String[] args) {
        AnimalInvocationHandler handler = new AnimalInvocationHandler(new Cat());
        Animal animalProxy = JDKProxyFactory.getAnimalProxy("cat",handler);
        animalProxy.move();

    }
}
