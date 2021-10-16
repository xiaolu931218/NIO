package com.test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;


public class TestChannelWrite {

    /**
     * 使用channel写到buffer
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        RandomAccessFile aFile = new RandomAccessFile("F:\\tmp\\write.txt", "rw");

        // 创建filechannel
        FileChannel fileChannel = aFile.getChannel();

        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
        String newData = "ABC123\n\n我是大笨蛋";
        byteBuffer.put(newData.getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        System.out.println("写入开始！！");
        // 读取数据到buffer中
        while (byteBuffer.hasRemaining()) {
            fileChannel.write(byteBuffer);
        }
        fileChannel.close();
        System.out.println("写入结束！！");
    }


}
