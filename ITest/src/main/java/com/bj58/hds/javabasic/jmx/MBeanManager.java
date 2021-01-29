package com.bj58.hds.javabasic.jmx;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanInfo;
import java.util.HashMap;


/**
 * JDK根据JMX在java.lanng.management包中提供虚拟机内存分配，GC，操作系统层，线程调度，共享锁，编译期情况等状态的查询接口
 * ClassLoadingMXBean 类加载信息
 * CompilationMXBean  编译器和编译状况
 * GarbageCollectorMXBean GC情况
 * MemoryPoolMXBean 内存池信息
 * OperatingSystemMXBean 操作系统信息
 * RuntimeMXBean 运行时信息：jvm名称，提供商，版本，classpath，bootclasspath及系统参数
 * ThreadMXBean 线程状态，cpu占用
 * */
public class MBeanManager {

    private static MBeanServer mBeanServer;

    private static HashMap<String,ObjectName> nameMap;
    static{
        //初始化MBeanServer
        mBeanServer = MBeanServerFactory.createMBeanServer();
        //初始化nameMap
        nameMap = new HashMap<>();
    }

    public static void main(String[] args) throws Exception {
        ServerImpl serverImpl = new ServerImpl();
        ServerMonitor serverMonitor = new ServerMonitor(serverImpl);
        MBeanManager.registerMBean("objectName:id=ServerMonitor1",serverMonitor);//StandardMBean

        DynamicServerMonitor dynamicServerMonitor = new DynamicServerMonitor(serverImpl);
        MBeanManager.registerMBean("objectName:id=DynamicServerMonitor1",dynamicServerMonitor);//DynamicMBean

//        RequiredModelMBean requiredModelMBean = (RequiredModelMBean) mBeanServer.instantiate("javax.management.modelmbean.RequireModelMBean");
//        ModelMBeanInfo modelMBeanInfo = getModelMBeanInfo();
//        requiredModelMBean.setModelMBeanInfo(modelMBeanInfo);
//        MBeanManager.registerModelMBean("objectName:id=RequireModelBean1",modelMBeanInfo,requiredModelMBean);//RequireModelBean
        for(;;){
            long uptime = (long) MBeanManager.getAttribute("objectName:id=ServerMonitor1","UpTime");
            System.out.println("ServerMonitor1 get upTime="+uptime);
            uptime = (long) MBeanManager.getAttribute("objectName:id=DynamicServerMonitor1","UpTime");
            System.out.println("DynamicServerMonitor1 get upTime="+uptime);
            Thread.sleep(1000);
        }

    }

    /**
     * @param name 监视器的名称，形如：objectName:id=xxxx
     * @param mBean 待注册的mBean
     * */
    public static void registerMBean(String name,Object mBean) throws Exception{
        ObjectName objectName = new ObjectName(name);
        nameMap.put(name,objectName);
        /**
         * 在注册MBean的过程中会将所有类型的MBean都转化为DynamicMBean，该过程由Introspector完成
         * 常规MBean则需要通过生成一些属性对应的方法(根据接口获取对应的公开方法)
         * DynamicMBean则需要实现自己的getAttribute逻辑，以提供属性的获取能力
         * DynamicMBean可以适配一些不按照标准的MBean
         * OpenMBean的公开接口的参数和返回值只能是基本类型或包内的ArrayType
         * */
//        monitorClass.getConstructor(cls).newInstance(cls.getConstructor().newInstance())
        mBeanServer.registerMBean(mBean,objectName);
    }

    /**
     * todo 注册RequireModelMBean
     * */
    public static void registerModelMBean(String name, ModelMBeanInfo modelMBeanInfo, ModelMBean modelMBean) throws MalformedObjectNameException {
        ObjectName objectName = new ObjectName(name);
        nameMap.put(name,objectName);

    }

    /**
     * @param name 监视器的名称，形如：objectName:id=xxxx
     * @param property 想获取的监测器监测的属性
     * */
    private static Object getAttribute(String name,String property) throws Exception{
        return mBeanServer.getAttribute(nameMap.get(name),property);
    }
}
