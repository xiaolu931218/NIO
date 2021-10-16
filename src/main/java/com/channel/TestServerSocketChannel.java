package com.channel;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TestServerSocketChannel {
    public static void main(String[] args) throws IOException, InterruptedException {
        //监听端口
        int port = 8888;
        ByteBuffer buffer = ByteBuffer.wrap("Hello World".getBytes(StandardCharsets.UTF_8));
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 绑定
        ssc.socket().bind(new InetSocketAddress(port));
        // 设置非阻塞模式
        ssc.configureBlocking(true);
        // 监听新链接传入
        while (true) {
            System.out.println("waiting for new connection...");
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("none connection");
                Thread.sleep(10000);
            }else {
                System.out.println("Incoming new connection from" + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
