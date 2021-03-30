package com.bj58.hds.jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

public class FileClassLoader extends ClassLoader{

    //加载class的根路径
    private String rootPath;

    protected Class<?> findClass(String className) throws ClassNotFoundException{
        byte[] classData = loadClassData(className);
        if(classData == null){
            throw new ClassNotFoundException();
        }else{
            return defineClass(className,classData,0,classData.length);
        }
    }

    private byte[] loadClassData(String className) {
        String fileName = rootPath + File.separatorChar + className.replace('.',File.separatorChar) + ".class";
        try{
            InputStream in = new FileInputStream(fileName);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while((length=in.read(buffer)) != -1){
                out.write(buffer,0,length);
            }
            return out.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getRootPath(){
        return this.rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        return loadClass(name, false);
//    }
//
//    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        synchronized (getClassLoadingLock(name)) {//同步块加锁的对象是从一个ConcurrentHashMap中取出的一个Object对象，这个map中以类的全限定名为key，一个Object为value
//            //首先检查类是否已加载
//            Class<?> c = findLoadedClass(name);
//            if (c == null) {
//                try {
//                    if (parent != null) {//父加载器不为空，则委托父加载器加载该类
//                        c = parent.loadClass(name, false);
//                    } else {//父加载器为空，则直接委托启动类加载器进行加载该类
//                        c = findBootstrapClassOrNull(name);
//                    }
//                } catch (ClassNotFoundException e) {
//
//                }
//
//                if (c == null) {//均无法加载时，自己进行类加载。
//                    c = findClass(name);//findClass是自定义类加载器需要重写的方法以此来自定义类加载的逻辑。
//                }
//            }
//            if (resolve) {
//                resolveClass(c);
//            }
//            return c;
//        }
//    }

//    类初始化的触发条件

//    1、创建类的实例，也就是new的方式
//
//    2、访问某个类或接口的静态变量，或者对该静态变量赋值
//
//    3、调用类的静态方法
//
//    4、反射，如Class.forName(“com.shengsiyuan.Test”)
//
//    5、初始化某个类的子类，则其父类也会被初始化
//
//    6、Java虚拟机启动时被标明为启动类的类，直接使用 java.exe命令来运行某个主类


    public static void main(String[] args){
        FileClassLoader fileClassLoader = new FileClassLoader();
        fileClassLoader.setRootPath("D:\\");
        Class<?> carClass = null;
        try{
//            carClass = Class.forName("com.bj58.hds.vmtest.Car",false,fileClassLoader);//通过指定是否初始化，来强制是否执行类的初始化流程
//            carClass = Class.forName("com.bj58.hds.vmtest.Car");//等同于Class.forName("com.bj58.hds.vmtest.Car",true,fileClassLoader);不过类加载器会根据调用者的类加载器设置
            //通过类加载器加载类时并不会触发类的初始化，不会执行静态块，只加载。
            //加载完成后会将二进制的字节流存放到方法区，在堆区则会生成一个java.lang.Class对象封装类在方法去内的数据结构并提供访问方法区数据的接口
            carClass = fileClassLoader.loadClass("com.bj58.hds.jvm.Car");
            Method getMethod = carClass.getMethod("getName");
            Method setMethod = carClass.getMethod("setName",String.class);
            System.out.println("未触发静态代码块");
            Object car = carClass.newInstance();//newInstance()方法会执行构造方法，因此可以触发类的初始化，进而执行static代码块
            System.out.println("触发静态代码块");
            setMethod.invoke(car,"哈哈");
            System.out.println(getMethod.invoke(car));
            System.out.println(car.getClass());
            System.out.println(car.getClass().getClassLoader());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
