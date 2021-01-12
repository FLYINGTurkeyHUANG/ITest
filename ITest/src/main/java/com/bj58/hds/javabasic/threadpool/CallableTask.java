package com.bj58.hds.javabasic.threadpool;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<Result> {
    int id;

    public CallableTask(int id) {
        this.id = id;
    }

    @Override
    public Result call() throws Exception {
        if (id % 2 == 0) {//判断结果顺序是否与任务顺序一致
            Thread.sleep(5000);
        } else {
            Thread.sleep(1000);
        }
        return new Result("任务" + id + "执行完成");
    }
}
