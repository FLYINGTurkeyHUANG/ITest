package com.bj58.hds.spring.container;

import com.bj58.hds.spring.interfaces.NameGernerator;

public class DefaultNameGernerator implements NameGernerator {
    @Override
    public String getName(String name) {
        name = name.substring(name.lastIndexOf(".")+1);
        byte[] bytes = name.getBytes();
        bytes[0] = (byte) (bytes[0]+32);
        name = new String(bytes);
        return name;
    }
}
