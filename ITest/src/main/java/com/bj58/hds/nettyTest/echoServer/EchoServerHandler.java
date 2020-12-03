package com.bj58.hds.nettyTest.echoServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 重写ChannelInboundHandlerAdapter的channelRead方法以实现新的逻辑
     * @param context   通道处理器的上下文
     * @param message   消息
     * */
    @Override
    public void channelRead(ChannelHandlerContext context, Object message){
        context.write(message);
    }

    /**
     * 重写ChannelInboundHandlerAdapter的channelReadComplete方法在完成消息的读取后刷新缓冲区
     * @param context 通道处理器上下文
     * */
    public void channelReadComplete(ChannelHandlerContext context){
        context.flush();
    }
}
