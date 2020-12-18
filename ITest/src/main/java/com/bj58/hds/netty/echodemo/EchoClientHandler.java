package com.bj58.hds.netty.echodemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * 客户端处理器
 * */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;

    /**
     * 创建客户端的处理器,并在建立连接后向服务端连续发送消息
     */
    public EchoClientHandler() {
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte((byte) i);
        }
    }

    /**
     * 重写ChannelInboundHandlerAdapter的channelActive实现在通道处于活跃状态时的回调逻辑
     * @param context   通道处理器的上下文
     * */
    @Override
    public void channelActive(ChannelHandlerContext context) {
        context.writeAndFlush(firstMessage);
    }

    /**
     * 重写ChannelInboundHandlerAdapter的channelRead实现消息读取的逻辑
     * @param context   通道处理器的上下文
     * @param message   消息
     * */
    @Override
    public void channelRead(ChannelHandlerContext context, Object message) {
        context.write(message);
    }

    /**
     * 重写ChannelInboundHandlerAdapter的channelReadComplete实现消息读取完成后的逻辑
     * @param context   通道处理器的上下文
     * */
    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        context.flush();
    }

    /**
     * 重写ChannelInboundHandlerAdapter的exceptionCaught实现新的异常处理逻辑
     * @param context   通道处理器的上下文
     * @param cause     出现的问题
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        context.close();
    }
}
