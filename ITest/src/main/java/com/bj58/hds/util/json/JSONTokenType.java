package com.bj58.hds.util.json;

public enum JSONTokenType {
    BEGIN_OBJECT(1),//左大括号 {
    END_OBJECT(2),//右大括号 }
    BEGIN_ARRAY(3),//左中括号 [
    END_ARRAY(4),//右中括号]
    NULL(5),//null
    NUMBER(6),//数字
    STRING(7),//字符串
    BOOLEAN(8),//布尔值 true false
    SEP_COLON(9),//冒号 :
    SEP_COMMA(10),//逗号 ,
    END_DOCUMENT(12);//结束

    private int code;

    JSONTokenType(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
