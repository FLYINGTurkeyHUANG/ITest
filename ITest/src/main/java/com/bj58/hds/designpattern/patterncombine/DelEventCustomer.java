package com.bj58.hds.designpattern.patterncombine;

public class DelEventCustomer extends EventCustomer {

    public DelEventCustomer(){
        super(EventCustomerType.DEL_PRODUCT);
    }

    @Override
    public void exec(ProductEvent event) {
        Product product = event.getSource();
        ProductEventType type = event.getType();
        if(type.getType() == EventCustomerType.DEL_PRODUCT.getType()){
            System.out.println(this.getClass().getName()+"处理事件:"+product.getName()+"克隆，事件类型="+type);
        }else{
            System.out.println(this.getClass().getName()+"暂不支持,type="+type);
        }
    }
}
