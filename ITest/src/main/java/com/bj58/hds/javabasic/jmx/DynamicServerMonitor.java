package com.bj58.hds.javabasic.jmx;

import javax.management.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class DynamicServerMonitor implements DynamicMBean {

    private final ServerImpl target;

    private MBeanInfo mBeanInfo;

    public DynamicServerMonitor(ServerImpl serverImpl){
        this.target = serverImpl;
    }

    public long UpTime(){
        return System.currentTimeMillis() - target.startTime;
    }


    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if("UpTime".equals(attribute)){
            return UpTime();
        }
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {

    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        if(mBeanInfo == null){
            try{
                Class cls = this.getClass();
                Method readMethod = cls.getMethod("UpTime",new Class[0]);
                Constructor constructor = cls.getConstructor(new Class[]{ServerImpl.class});
                MBeanAttributeInfo upTimeMBeanAttributeInfo = new MBeanAttributeInfo("UpTime","The time span since server start",readMethod,null);
                MBeanConstructorInfo serverImplConstructorInfo = new MBeanConstructorInfo("Constructor for DynamicServerMonitor",constructor);
                mBeanInfo = new MBeanInfo(cls.getName(),"DynamicServerMonitor",new MBeanAttributeInfo[]{upTimeMBeanAttributeInfo},new MBeanConstructorInfo[]{serverImplConstructorInfo},null,null);
            }catch(Exception e){
                throw new Error(e);
            }
        }
        return mBeanInfo;
    }
}
