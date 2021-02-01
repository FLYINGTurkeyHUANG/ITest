package com.bj58.hds.patterncombine;

/**
 * 消费类型需要包含事件类型
 * */
public enum EventCustomerType {

    /** 创建事件 */
    NEW_PRODUCT(1),

    /** 废弃事件 */
    DEL_PRODUCT(2),

    /** 修改事件 */
    MODIFY_PRODUCT(3),

    /** 克隆事件 */
    CLONE_PRODUCT(4);

    /** 类型 */
    private int type;

    EventCustomerType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

}
