package com.bj58.hds.spring.container;

import com.bj58.hds.spring.interfaces.Animal;
import com.bj58.hds.spring.interfaces.Context;
import com.bj58.hds.spring.interfaces.NameGernerator;
import com.bj58.hds.spring.util.PackageUtil;

import java.util.HashMap;
import java.util.List;

public class ApplicationContext implements Context {

    private HashMap<String,Object> map;

    public ApplicationContext(){
        init();
    }

    /**
     * 初始化容器
     * */
    @Override
    public void init(){
        //初始化map
        map = new HashMap<>();
        //扫描component包下所有的组件，反射生成对象并放入map中
        scan("com.bj58.hds.spring.component",new DefaultNameGernerator());
//        for(HashMap.Entry entry:map.entrySet()){
//            System.out.println(entry.getKey().toString()+" "+entry.getValue().toString());
//        }
    }

    /**
     * 获取一个bean
     * @param name  bean的名称
     * */
    @Override
    public Object get(String name){
        return map.get(name);
    }

    /**
     * 添加一个bean
     * @param name  bean的名称
     * @param bean  bean对象
     * */
    @Override
    public void add(String name,Object bean){
        map.put(name,bean);
    }

    /**
     * 获取包下及其子包下所有的类名
     * @param packageName   包的全路径名
     * */
    private void scan(String packageName, NameGernerator nameGernerator){
        //获取包下的类名
        try{
            List<String> className = PackageUtil.getClassName(packageName);
            for(String name:className){
//                System.out.println(System.getProperty("user.dir"));
//                System.out.println(name);
                //反射加载类并生成对象
                Class cls = getClass().getClassLoader().loadClass(name);
                Object object = cls.newInstance();
                map.put(nameGernerator.getName(name),object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        ApplicationContext applicationContext = new ApplicationContext();
        Animal animal = (Animal) applicationContext.get("cat");
        animal.move();
        animal.play();
        animal.sleep();
    }

}
