package com.channel;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * 测试DatagramChannel，实现UDP
 */
public class TestDatagramChannel {
    //发送的实现
    @Test
    public void sendDatagram() throws Exception {
        //打开 DatagramChannel
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress =
                new InetSocketAddress("127.0.0.1",9999);
        //发送
        while(true) {
            ByteBuffer buffer = ByteBuffer.wrap("发送atguigu".getBytes("UTF-8"));
            sendChannel.send(buffer, sendAddress);
            System.out.println("已经完成发送");
            Thread.sleep(1000);
        }
    }

    //接收的实现
    @Test
    public void receiveDatagram() throws Exception {
        //打开DatagramChannel
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress(9999);
        //绑定
        receiveChannel.bind(receiveAddress);
        //buffer
        ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);
        //接收
        while(true) {
            receiveBuffer.clear();
            SocketAddress socketAddress = receiveChannel.receive(receiveBuffer);
            receiveBuffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(Charset.forName("UTF-8").decode(receiveBuffer));
        }
    }
}
