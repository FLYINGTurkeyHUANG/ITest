package com.bj58.hds.template;

import com.alibaba.fastjson.JSONObject;
import com.bj58.hds.spring.util.TextGeneratorUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


/**
 * 简历发布模板页生成
 * */
public class Main {

    public static JSONObject templateConfig;
    static{
        StringBuilder config = new StringBuilder();
        try {

            BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/ITest/src/main/resources/config/templateConfig"));
            String str;
            while ((str = in.readLine()) != null) {
                config.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        templateConfig = JSONObject.parseObject(config.toString());
    }
    public static void main(String[] args) {
        TextGeneratorUtil.generate("driver",System.getProperty("user.dir")+"/ITest/src/main/resources/view/driver.html",(Map) templateConfig);
    }
}
