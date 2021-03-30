package com.bj58.hds.designpattern.patterncombine;

public class ProductFactory {
    /** 是否可以创建一个产品 */
    private boolean permittedCreate = false;

    /** 创建一个产品 */
    public Product createProduct(String name,String type) throws Exception{
        //修改权限，允许创建
        permittedCreate = true;
        Product product;
        if("A".equals(type)){
            product= new ProductA(this,name);
        }else if("B".equals(type)){
            product= new ProductB(this,name);
        }else{
            throw new Exception("不支持的产品类型");
        }

        //产生一个创建事件
        new ProductEvent(product,ProductEventType.NEW_PRODUCT);
        return product;
    }

    /** 废弃一个产品 */
    public void abandonProduct(Product product){
        //废弃一个产品
        new ProductEvent(product,ProductEventType.DEL_PRODUCT);
        product = null;
    }

    /** 修改一个产品 */
    public void modifyProduct(Product product,String name){
        product.setName(name);
        //产生修改事件
        new ProductEvent(product,ProductEventType.MODIFY_PRODUCT);
    }

    /** 是否可以创建一个产品 */
    public boolean isPermittedCreate(){
        return permittedCreate;
    }

    /** 克隆一个产品 */
    public Product clone(Product product){
        //产生克隆事件
        new ProductEvent(product,ProductEventType.CLONE_PRODUCT);
        return product.clone();
    }
}
