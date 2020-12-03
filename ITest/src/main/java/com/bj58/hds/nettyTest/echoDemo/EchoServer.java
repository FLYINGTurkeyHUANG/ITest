package com.bj58.hds.nettyTest.echoDemo;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.util.Scanner;

/**
 * 输出任何来自客户端的数据
 * */
public class EchoServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port","8007"));

    public static void main(String[] args) throws Exception {
        //配置SSL
        final SslContext sslContext;
        if(SSL){
            SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
            //取出证书、私钥
            sslContext = SslContextBuilder.forServer(selfSignedCertificate.certificate(),selfSignedCertificate.privateKey()).build();
        }else{
            sslContext = null;
        }

        //配置服务器
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final EchoServerHandler echoServerHandler = new EchoServerHandler();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)    //配置事件循环组
                    .channel(NioServerSocketChannel.class)  //选择通道类型
                    .option(ChannelOption.SO_BACKLOG,100)   //配置日志选项
                    .handler(new LoggingHandler(LogLevel.INFO))    //配置日志输出级别
                    .childHandler(new ChannelInitializer<SocketChannel>() { //配置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            if(sslContext != null){
                                pipeline.addLast(sslContext.newHandler(ch.alloc()));
                            }
                            pipeline.addLast(echoServerHandler);
                        }
                    });

            //启动服务器
            ChannelFuture future =  serverBootstrap.bind(PORT).sync();
            //在服务器关闭后关闭通道
            future.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            //关闭所有的事件循环以终止所有的线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
