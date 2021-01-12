package com.bj58.hds.javabasic.threadpool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args){
        HeapSort heapSort = new HeapSort();
        heapSort.testTakeAndPut();

        //invokeAll()触发执行任务列表，返回的结果顺序与任务列表的顺序一致，所有线程都执行完才返回结果，有超时选项。
        ExecutorService threadPool = new ThreadPoolExecutor(2,8,5, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        List<CallableTask> taskList = new ArrayList<>();
        for (int i=0;i<10;i++){
            taskList.add(new CallableTask(i));
        }
        List<Future<Result>> results = null;
        System.out.println(new Date());
        try{
            results = threadPool.invokeAll(taskList);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(new Date());
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(results.get(i).get().getRes());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //invokeAny()触发执行任务列表，将一个得到的结果作为返回值，然后终止所有的线程，有超时选项。
        Result result = null;
        try {
            result = threadPool.invokeAny(taskList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.getRes());
        System.out.println(new Date());
        threadPool.shutdown();


    }

}
