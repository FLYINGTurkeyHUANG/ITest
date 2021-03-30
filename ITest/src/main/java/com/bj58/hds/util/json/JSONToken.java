package com.bj58.hds.util.json;

public class JSONToken {

    /**
     * Token类型，详见JSONTokenType
     * */
    private JSONTokenType tokenType;

    /**
     * Token存储的值
     * */
    private String value;

    public JSONToken(JSONTokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public JSONTokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    public void setTokenType(JSONTokenType tokenType) {
        this.tokenType = tokenType;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
