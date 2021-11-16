package powernode;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TestSocketChannel {

    /**
     * 测试ServerSocketChannel，服务端代码
     */
    @Test
    public void test06() throws IOException, InterruptedException {

        // ServerSocket的端口号
        int port = 8080;
        // 创建一个未绑定到ServerSocket服务器的channel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 把ssc绑定到端口上
        ssc.socket().bind(new InetSocketAddress(port));
        // 设置通道为非阻塞模式
        ssc.configureBlocking(false);
        while (true) {
            System.out.println("我是ServerSocket，已经准备好了，就等你来了！");
            SocketChannel sc = ssc.accept();
            // 如果没有连接，sc为null
            if (sc == null) {
                Thread.sleep(1000);

            }else{
                // 有连接先给客户端发送消息
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.put("hello,I am from socket server".getBytes(StandardCharsets.UTF_8));
                buffer.flip();
                sc.write(buffer);
                System.out.println("from client : "+sc.socket().getRemoteSocketAddress());
                buffer.clear();
                // 打印客户端发来的信息
                sc.read(buffer);
                buffer.flip();
                CharBuffer charBuffer = Charset.defaultCharset().decode(buffer);
                System.out.println("客户端发来：" + charBuffer);
                // 关闭资源
                sc.close();
            }
        }
    }


    /**
     * 测试SocketChannel，客户端代码
     */
    @Test
    public void test07() throws IOException {

        // ServerSocket的ip
        String host = "127.0.0.1";
        // ServerSocket的端口号
        int port = 8080;
        // 服务端地址对象
        InetSocketAddress address = new InetSocketAddress(host, port);
        // 创建一个未绑定到ServerSocket服务器的channel
        SocketChannel sc = SocketChannel.open();
        // 连接到服务器端
        sc.connect(address);
        while (!sc.finishConnect()) {
            System.out.println("等待连接...");
        }
        System.out.println("连接成功！");

        // 给服务端发送信息
        ByteBuffer buffer = ByteBuffer.wrap("hello,I am client ".getBytes(StandardCharsets.UTF_8));
        while (buffer.hasRemaining()) {
            sc.write(buffer);
        }
        buffer.clear();
        // 获取服务端发给客户端的信息
        InputStream inputStream = sc.socket().getInputStream();
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        ByteBuffer serverBuffer = ByteBuffer.allocate(1024);
        readableByteChannel.read(serverBuffer);
        serverBuffer.flip();
        CharBuffer charBuffer = Charset.defaultCharset().decode(serverBuffer);
        System.out.println("服务端发来的消息：" + charBuffer);

        sc.close();
    }
}
