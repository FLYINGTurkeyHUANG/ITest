package com.bj58.hds.spring.proxy.interceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AnimalMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("proxy invoke "+method.getName()+" start...");
        Object result = methodProxy.invokeSuper(object,args);
        System.out.println("proxy invoke "+method.getName()+" end...");
        return result;
    }
}
