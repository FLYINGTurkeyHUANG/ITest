package com.bj58.hds.nettytest.echodemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端处理器
 * */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 重写ChannelInboundHandlerAdapter的channelRead方法实现新的消息读取逻辑
     * @param context   通道处理器的上下文
     * @param message   消息
     * */
    @Override
    public void channelRead(ChannelHandlerContext context, Object message){
        ByteBuf msg = (ByteBuf) message;
        for(int i=0;i<256;i++){
            System.out.println("server received:"+msg.getByte(i));
        }

        context.write(message);
    }

    /**
     * 重写ChannelInboundHandlerAdapter的channelReadComplete方法在完成消息的读取后刷新缓冲区
     * @param context   通道处理器上下文
     * */
    public void channelReadComplete(ChannelHandlerContext context){
        context.flush();
    }

    /**
     * 重写ChannelInboundHandlerAdapter的exceptionCaught方法实现新的异常处理
     * @param context   通道处理器上下文
     * @param cause     出现的问题
     * */
    public void exceptionCaught(ChannelHandlerContext context,Throwable cause){
        cause.printStackTrace();
        context.close();
    }
}
