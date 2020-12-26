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

    public static void generate(String template, String targetDir) {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(System.getProperty("user.dir")+"/ITest/src/main/resources/templates"));

            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> interfaces = new ArrayList<>();
            interfaces.add("Animal");
            interfaces.add("Mobile");
            dataMap.put("interfaces",interfaces);

            // step4 加载模版文件
            Template templates = configuration.getTemplate("JDKProxyFactory.ftl");

            // step5 生成数据
            File docFile = new File( System.getProperty("user.dir")+"/ITest/src/main/java/com/bj58/hds/spring/proxy/factory/JDKProxyFactory.java");
            System.out.println("docFile="+docFile.getPath());
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));

            // step6 输出文件
            templates.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^JDKProxyFactory.java 文件创建成功 !");
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

        System.out.println("D:/learnspace/ITest/ITest/src/main/java/com/bj58/hds/spring/proxy/factory/JDKProxyFactory.java");
        System.out.println(System.getProperty("user.dir"));
        File dir = new File("");
        System.out.println(dir.getAbsolutePath());
        System.out.println(dir.getCanonicalPath());

        generate("","");
    }

}
