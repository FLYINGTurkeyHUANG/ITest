package com.bj58.hds.patterncombine;

public abstract class Product implements Cloneable {

    /** 产品名称 */
    private String name;

    /** 属性是否可变 */
    private boolean canChanged;

    /** 产品类型 */
    private String type;

    /** 生产一个新的产品 */
    public Product(ProductFactory productFactory,String name,String type){
        if(productFactory.isPermittedCreate()){
            canChanged = true;
            this.name = name;
            this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
