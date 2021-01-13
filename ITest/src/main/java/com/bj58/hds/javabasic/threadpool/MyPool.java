package com.bj58.hds.javabasic.threadpool;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.*;

public class MyPool extends ThreadPoolExecutor {

    public MyPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 重写beforeExecute可以在任务执行之前进行一些操作
     * */
    @Override
    public void beforeExecute(Thread t, Runnable r) {
        System.out.println("MyPool execute task start...Thread="+ t.getName()+",Runnable="+JSON.toJSONString(r));
    }

    /**
     * 重写afterExecute可以在任务执行完之后进行一些操作
     * */
    @Override
    public void afterExecute(Runnable r, Throwable t) {
        System.out.println("MyPool execute task end...Throwable"+ JSON.toJSONString(t)+",Runnable="+JSON.toJSONString(r));
    }
}
