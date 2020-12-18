package com.bj58.hds.spring.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包工具：支持扫描包下所有的类，获取类名
 * */
public class PackageUtil {

    /**
     * 获取包下及其子包下的类名
     * @param packageName 包的全路径名
     * */
    public static List<String> getClassName(String packageName) throws IOException {
        return getClassName(packageName,true);
    }

    /**
     * 获取包下的类名
     * @param packageName 包的全路径名
     * @param childRead   是否读取子包下的类名
     * */
    public static List<String> getClassName(String packageName, boolean childRead) throws IOException {
        List<String> className = new ArrayList<>();
        //获取当前线程的上下文类加载器进行资源加载
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', File.separatorChar);
        Enumeration<URL> urls = classLoader.getResources(path);
        while(urls.hasMoreElements()){
            URL url = urls.nextElement();
            if(url == null){
                continue;
            }
            String type = url.getProtocol();
            if("file".equals(type)){//文件类型则通过getClassNameByFile完成获取
                className.addAll(getClassNameByFile(url.getPath(),childRead));
            }else if("jar".equals(type)){//jar类型则通过getClassNameByJar完成获取
                className.addAll(getClassNameByJar(url.getPath(),childRead));
            }
        }
        className.addAll(getClassNameByJars(((URLClassLoader) classLoader).getURLs(),packageName,childRead));
        return className;
    }

    /**
     * 从项目文件中获取指定路径所有的类
     * @param filePath  file路径
     * @param childRead 是否读取子文件夹下的类
     * */
    private static List<String> getClassNameByFile(String filePath,boolean childRead) throws UnsupportedEncodingException {
        List<String> className = new ArrayList<>();
        filePath = URLDecoder.decode(filePath,"utf-8");
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if(childFiles == null){
            return className;
        }
        for(File childFile:childFiles){
            if(childFile.isDirectory()){//目录，则根据是否读取子文件夹下的值判断
                if(childRead){
                    className.addAll(getClassNameByFile(childFile.getPath(),childRead));
                }
            }else{
                String childFilePath = childFile.getPath();
                if(childFilePath.endsWith(".class")){
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes\\")+9,childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\",".");
                    className.add(childFilePath);
                }
            }
        }
        return className;
    }

    /**
     * 从jar中获取指定路径下所有的类
     * @param jarPath jar路径
     * @param childRead 是否读取子jar下的类
     * */
    private static List<String> getClassNameByJar(String jarPath,boolean childRead) throws UnsupportedEncodingException {
        List<String> className = new ArrayList<>();
        String[] jarInfo = jarPath.split("!");//需要从所有jar中搜索包的时候会拼接  jar路径!/包路径
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        jarFilePath = URLDecoder.decode(jarFilePath,"utf-8");
        String packagePath = jarInfo[1].substring(1);//切分后获取包路径
        try{
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()){
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if(entryName.endsWith(".class")){
                    if(childRead){
                        if(entryName.startsWith(packagePath)){
                            entryName = entryName.replace("\\",".").substring(0,entryName.lastIndexOf("."));
                            className.add(entryName);
                        }
                    }
                }else{
                    int index = entryName.lastIndexOf("\\");
                    String myPackagePath;
                    if (index != -1) {
                        myPackagePath = entryName.substring(0, index);
                    } else {
                        myPackagePath = entryName;
                    }
                    if (myPackagePath.equals(packagePath)) {
                        entryName = entryName.replace("\\", ".").substring(0, entryName.lastIndexOf("."));
                        className.add(entryName);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return className;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls         URL集合
     * @param packagePath  包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) throws UnsupportedEncodingException {
        List<String> className = new ArrayList<String>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                className.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return className;
    }

}
