package com.bj58.hds.designpattern.patterncombine;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class EventDispatch implements Observer {

    /** 创建单例的事件分发器 */
    private final static EventDispatch dispatch = new EventDispatch();

    /** 事件消费者列表 */
    private ArrayList<EventCustomer> customers = new ArrayList<EventCustomer>();

    /** 私有构造禁止生成新的示例 */
    private EventDispatch(){}

    /** 获取单例对象 */
    public static EventDispatch getEventDispatch(){
        return dispatch;
    }


    @Override
    public void update(Observable o,Object arg){
        //事件源
        Product product = (Product) arg;
        //事件
        ProductEvent event = (ProductEvent) o;
        //事件分发器作为中介者的核心逻辑在于此处，简化处理为for循环。
        for(EventCustomer e:customers){
            for(EventCustomerType type:e.getCustomType()){
                if(type.getType() == event.getType().getType()){
                    e.exec(event);
                }
            }
        }
    }

    public void registerCustomer(EventCustomer customer){
        customers.add(customer);
    }
}
