package com.bj58.hds.designpattern.patterncombine;

public class ModifyAndCloneEventCustomer extends EventCustomer{

    public ModifyAndCloneEventCustomer(){
        super(EventCustomerType.MODIFY_PRODUCT);
        super.addCustomType(EventCustomerType.CLONE_PRODUCT);
    }

    @Override
    public void exec(ProductEvent event) {
        Product product = event.getSource();
        ProductEventType type = event.getType();
        if(type.getType() == EventCustomerType.CLONE_PRODUCT.getType()){
            System.out.println(this.getClass().getName()+"处理事件:"+product.getName()+"克隆，事件类型="+type);
        }else if(type.getType() == EventCustomerType.MODIFY_PRODUCT.getType()){
            System.out.println(this.getClass().getName()+"处理事件:"+product.getName()+"克隆，事件类型="+type);
        }else{
            System.out.println(this.getClass().getName()+"暂不支持,type="+type);
        }
    }
}
