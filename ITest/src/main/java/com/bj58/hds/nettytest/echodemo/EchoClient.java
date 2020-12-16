package com.bj58.hds.nettytest.echodemo;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * 向服务端发送数据即可
 * */
public class EchoClient  {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host","127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port","8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size","256"));
    public static void main(String[] args) throws SSLException {

        //配置SSL
        final SslContext sslContext;
        if(SSL){
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        }else{
            sslContext = null;
        }

        //配置客户端
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            if(sslContext != null){
                                pipeline.addLast(sslContext.newHandler(ch.alloc(),HOST,PORT));
                            }
                            pipeline.addLast(new EchoClientHandler());
                        }
                    });
            //启动客户端
            ChannelFuture future = bootstrap.connect(HOST,PORT).sync();
//            Scanner in = new Scanner(System.in);
//            String msg = in.nextLine();


            //等待直到连接关闭
            future.channel().closeFuture().sync();

        }catch (Exception e){

        }finally {
            group.shutdownGracefully();
        }
    }



}