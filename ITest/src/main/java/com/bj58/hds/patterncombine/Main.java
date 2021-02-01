package com.bj58.hds.patterncombine;

public class Main {
    public static void main(String[] args) {
        //获得事件分发中心
        EventDispatch dispatch = EventDispatch.getEventDispatch();
        dispatch.registerCustomer(new ModifyAndCloneEventCustomer());
        dispatch.registerCustomer(new DelEventCustomer());
        dispatch.registerCustomer(new NewEventCustomer());
        ProductFactory factory = new ProductFactory();
        //制造一个产品
        System.out.println("=====模拟创建产品事件========");
        System.out.println("创建一个嫦娥卫星5号");
        Product p = factory.createProduct("嫦娥卫星5号");
        //修改一个产品
        System.out.println("\n=====模拟修改产品事件========");
        System.out.println("把嫦娥卫星5号修改为嫦娥卫星6号");
        factory.modifyProduct(p, "嫦娥卫星6号");
        //克隆一个产品
        System.out.println("\n=====模拟克隆产品事件========");
        System.out.println("克隆嫦娥卫星6号");
        factory.clone(p);
        //遗弃一个产品
        System.out.println("\n=====模拟销毁产品事件========");
        System.out.println("遗弃嫦娥卫星6号");

    }
}
