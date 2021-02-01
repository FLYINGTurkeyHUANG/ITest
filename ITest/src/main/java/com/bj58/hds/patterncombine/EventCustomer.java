package com.bj58.hds.patterncombine;

import java.util.ArrayList;

public abstract class EventCustomer {

    /** 消费者所能处理的事件类型 */
    private ArrayList<EventCustomerType> customType = new ArrayList<>();

    /** 消费者需要声明能消费的时间类型 */
    public EventCustomer(EventCustomerType type){
        addCustomType(type);
    }

    /** 添加可消费的事件类型 */
    public void addCustomType(EventCustomerType type){
        customType.add(type);
    }

    /** 获取能消费的事件类型列表 */
    public ArrayList<EventCustomerType> getCustomType(){
        return customType;
    }

    /** 需要通过重载或者选择结构，对每个可消费的事件类型做不同的处理 */
    public abstract void exec(ProductEvent event);
}
