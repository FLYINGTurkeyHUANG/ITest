package com.bj58.hds.spring.util;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供根据模板进行文本生成的功能
 */
public class TextGeneratorUtil {

    /** 模板文件的存放路径 */
    public static final String TEMPLATEDIR = System.getProperty("user.dir")+"/ITest/src/main/resources/templates";

    /**
     * 生成代理工厂的java文件
     * @param templateName 模板文件名
     * @param targetDir 生成文件的存放路径
     * @param data 模板文件中所需要的的参数
     * */
    public static void generateProxyFactory(String templateName, String targetDir,Map<String,Object> data) {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATEDIR));

            // step3 使用传入数据模型

            // step4 加载模版文件
            Template template = configuration.getTemplate(templateName+".ftl");

            // step5 生成数据
            File docFile = new File(targetDir);
            System.out.println("docFile="+docFile.getPath());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));

            // step6 输出文件
            template.process(data, out);
            System.out.println(templateName + " 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> interfaces = new ArrayList<>();
        interfaces.add("Animal");
        interfaces.add("Mobile");
        Map<String,Object> data = new HashMap<>();
        data.put("interfaces",interfaces);
        generateProxyFactory("JDKProxyFactory",System.getProperty("user.dir")+"/ITest/src/main/java/com/bj58/hds/spring/proxy/factory/JDKProxyFactory.java",data);
    }

}
