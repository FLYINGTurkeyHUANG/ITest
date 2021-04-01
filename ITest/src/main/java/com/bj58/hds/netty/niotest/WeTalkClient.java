package com.bj58.hds.netty.niotest;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * WeTalk 客户端
 */
public class WeTalkClient {

    private static final String EXIT_MARK = "exit";

    private String hostname;

    private int port;

    WeTalkClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
    //open() --> connect() --> ... --> close()
    public void start() throws IOException {
        // 打开一个网络套接字通道，并向服务端发起连接
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(hostname, port));

        Scanner sc = new Scanner(System.in);


        Thread t1 = new Thread(new Runnable() {
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

                    // 向服务端发送消息
                    try {
                        WeTalkUtils.sendMsg(channel, msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    // 接受服务端返回的消息
                    String msg = null;
                    try {
                        msg = WeTalkUtils.recvMsg(channel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("\n服务端：");
                    System.out.println(msg + "\n");
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
    }

    public static void main(String[] args) throws IOException {
        new WeTalkClient("localhost", 8080).start();
    }
}
