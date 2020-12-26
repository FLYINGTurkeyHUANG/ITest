package com.bj58.hds.spring.component;

import com.bj58.hds.spring.interfaces.Mobile;

public class Iphone implements Mobile {
    @Override
    public void call() {
        System.out.println("iphone call...");
    }

    @Override
    public void music() {
        System.out.println("iphone play music...");
    }

    @Override
    public void takePic() {
        System.out.println("iphone take picture...");
    }
}
