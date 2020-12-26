package com.bj58.hds.spring.component;

import com.bj58.hds.spring.interfaces.Mobile;

public class Huawei implements Mobile {
    @Override
    public void call() {
        System.out.println("huawei call...");
    }

    @Override
    public void music() {
        System.out.println("huawei play music...");
    }

    @Override
    public void takePic() {
        System.out.println("huawei take picture...");
    }
}
