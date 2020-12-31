package com.bj58.hds.spring.proxy.factory;

import com.bj58.hds.spring.container.ApplicationContext;
import com.bj58.hds.spring.interfaces.Animal;
import com.bj58.hds.spring.interfaces.Mobile;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class CGLIBProxyFactory {
    private static ApplicationContext context;

    public CGLIBProxyFactory(){}

    /**
     * 类似形式完全统一的代码可以使用文本生成技术直接生成即可。
     * */
    public static Animal getAnimalProxy(String target, MethodInterceptor interceptor){
        if(context == null){
            synchronized (JDKProxyFactory.class){
                context = new ApplicationContext();
            }
        }
        Object targetObject = context.get(target);
        if(targetObject == null){
            //全局获取该类的字节码，并生成对象放入context中
        }
        return (Animal) creatProxy(targetObject.getClass(),interceptor);
    }

    public static Mobile getMobileProxy(String target, MethodInterceptor interceptor){
        if(context == null){
            synchronized (JDKProxyFactory.class){
                context = new ApplicationContext();
            }
        }
        Object targetObject = context.get(target);
        if(targetObject == null){
            //全局获取该类的字节码，并生成对象放入context中
        }
        return (Mobile) creatProxy(targetObject.getClass(),interceptor);
    }

    private static Object creatProxy(Class cls, MethodInterceptor interceptor) {
        //设置生成class的文件路径
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\cglib");
        Enhancer enhancer = new Enhancer();
        //设置目标类(父类)的字节码文件，通过继承的方式
        enhancer.setSuperclass(cls);
        //设置回调函数，即拦截器，代理部分的逻辑在拦截器的intercept中完成
        enhancer.setCallback(interceptor);
        //会在指定的目录下生成三个字节码文件：1、代理类的FastClass 2、代理类 3、目标类的FastClass。FastClass机制用于避免频繁使用反射导致的效率降低
        return enhancer.create();
    }

}
