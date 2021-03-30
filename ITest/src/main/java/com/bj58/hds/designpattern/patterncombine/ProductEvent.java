package com.bj58.hds.designpattern.patterncombine;

import java.util.Observable;

public class ProductEvent extends Observable {

    /** 产生事件的产品(事件源) */
    private Product source;

    /** 事件类型 */
    private ProductEventType type;

    /** 默认新建 */
    public ProductEvent(Product source){
        this(source,ProductEventType.NEW_PRODUCT);
    }

    public ProductEvent(Product source,ProductEventType type){
        this.source = source;
        this.type = type;
        notifyEventDispatch();
    }

    public Product getSource() {
        return source;
    }

    public ProductEventType getType() {
        return type;
    }

    public void setSource(Product source) {
        this.source = source;
    }

    public void setType(ProductEventType type) {
        this.type = type;
    }

    public void notifyEventDispatch(){
        super.addObserver(EventDispatch.getEventDispatch());
        super.setChanged();
        super.notifyObservers(source);
    }
}
