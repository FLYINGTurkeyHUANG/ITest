package com.bj58.hds.spring.interfaces;

/**
 * bean的ID生成器，通过实现该接口实现不一样的ID生成方式
 * */
public interface NameGernerator {
    public String getName(String name);
}
