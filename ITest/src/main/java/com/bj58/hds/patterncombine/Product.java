package com.bj58.hds.patterncombine;

public class Product implements Cloneable {

    /** 产品名称 */
    private String name;

    /** 属性是否可变 */
    private boolean canChanged;

    /** 生产一个新的产品 */
    public Product(ProductFactory productFactory,String name){
        if(productFactory.isPermittedCreate()){
            canChanged = true;
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(canChanged){
            this.name = name;
        }
    }

    @Override
    public Product clone(){
        Product product = null;
        try{
            product = (Product) super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return product;
    }
}
