package com.bj58.hds.spring.interfaces;

public interface Context {
    /**
     * 初始化容器
     * */
    void init();

    /**
     * 从容器中获取一个bean
     * */
    Object get(String name);

    /**
     * 向容器中添加一个bean
     * */
    void add(String name,Object bean);
}
