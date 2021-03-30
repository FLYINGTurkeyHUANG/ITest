package com.bj58.hds.designpattern.patterncombine;

public enum ProductEventType {

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

    ProductEventType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
