package com.bj58.hds.spring.proxy.factory;

import com.bj58.hds.spring.container.ApplicationContext;
import com.bj58.hds.spring.interfaces.Animal;
import com.bj58.hds.spring.interfaces.Mobile;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
<#--传入接口的名称list即可-->
public class JDKProxyFactory {

    private static ApplicationContext context;

    public JDKProxyFactory(){}

    /**
    * 类似形式完全统一的代码可以使用文本生成技术直接生成即可。
    * */
<#list interfaces as interface>
    public static ${interface} get${interface}Proxy(String target,InvocationHandler handler){
        if(context == null){
            synchronized (JDKProxyFactory.class){
                context = new ApplicationContext();
            }
        }
        Object targetObject = context.get(target);
        if(targetObject == null){
        //全局获取该类的字节码，并生成对象放入context中
        }
        return (${interface})Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),targetObject.getClass().getInterfaces(),handler);
    }
</#list>
}