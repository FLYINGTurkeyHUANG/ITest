package com.bj58.hds.spring.proxy;


import com.bj58.hds.spring.component.Cat;
import com.bj58.hds.spring.interfaces.Animal;
import com.bj58.hds.spring.proxy.factory.CGLIBProxyFactory;
import com.bj58.hds.spring.proxy.factory.JDKProxyFactory;
import com.bj58.hds.spring.proxy.handler.AnimalInvocationHandler;
import com.bj58.hds.spring.proxy.interceptor.AnimalMethodInterceptor;

/**
 * 两种代理方式的示例
 * */
public class ProxyTest {

    public static void main(String[] args) {
        AnimalInvocationHandler handler = new AnimalInvocationHandler(new Cat());
        Animal animalProxy = JDKProxyFactory.getAnimalProxy("cat",handler);
        animalProxy.move();

        animalProxy = CGLIBProxyFactory.getAnimalProxy("cat",new AnimalMethodInterceptor());
        animalProxy.move();
    }
}
