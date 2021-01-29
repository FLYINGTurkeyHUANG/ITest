package com.bj58.hds.javabasic.jmx;

public class ServerMonitor implements ServerMonitorMBean {
    private final ServerImpl target;

    public ServerMonitor(ServerImpl target){
        this.target = target;
    }

    @Override
    public long getUpTime() {
        return System.currentTimeMillis()-target.startTime;
    }
}
