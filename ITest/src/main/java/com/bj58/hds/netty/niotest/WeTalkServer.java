package com.bj58.hds.netty.niotest;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * WeTalk 服务端
 */
public class WeTalkServer {

    private static final String EXIT_MARK = "exit";

    private int port;

    WeTalkServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        // 创建服务端套接字通道，监听端口，并等待客户端连接
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(port));
        System.out.println("服务端已启动，正在监听 " + port + " 端口......");
        SocketChannel channel = ssc.accept();
        System.out.println("接受来自" + channel.getRemoteAddress().toString().replace("/", "") + " 请求");

        Scanner sc = new Scanner(System.in);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    // 等待并接收客户端发送的消息
                    String msg = null;
                    try {
                        msg = WeTalkUtils.recvMsg(channel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("\n客户端：");
                    System.out.println(msg + "\n");
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    // 输入信息
                    System.out.println("请输入：");
                    String msg = sc.nextLine();
                    if (EXIT_MARK.equals(msg)) {
                        try {
                            WeTalkUtils.sendMsg(channel, "bye~");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    // 回复客户端消息
                    try {
                        WeTalkUtils.sendMsg(channel, msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 关闭通道
        channel.close();
        ssc.close();
    }

    public static void main(String[] args) throws IOException {
        new WeTalkServer(8080).start();
    }
}
