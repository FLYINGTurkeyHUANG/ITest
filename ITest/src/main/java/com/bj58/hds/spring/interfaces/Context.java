package com.bj58.hds.spring.interfaces;

public interface Context {
    public void init();
    public Object get(String name);
    public void add(String name,Object bean);
}
