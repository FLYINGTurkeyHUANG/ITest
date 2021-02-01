package com.bj58.hds.patterncombine;

public class NewEventCustomer extends EventCustomer {

    public NewEventCustomer(){
        super(EventCustomerType.NEW_PRODUCT);
    }

    @Override
    public void exec(ProductEvent event) {
        Product product = event.getSource();
        ProductEventType type = event.getType();
        if(type.getType() == EventCustomerType.NEW_PRODUCT.getType()){
            System.out.println(this.getClass().getName()+"处理事件:"+product.getName()+"克隆，事件类型="+type);
        }else{
            System.out.println(this.getClass().getName()+"暂不支持,type="+type);
        }
    }
}
