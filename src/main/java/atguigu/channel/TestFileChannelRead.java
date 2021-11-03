package atguigu.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class TestFileChannelRead {

    /**
     * 使用channel读取到buffer
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        RandomAccessFile aFile = new RandomAccessFile("F:\\tmp\\aaa.txt", "rw");

        // 创建filechannel
        FileChannel fileChannel = aFile.getChannel();

        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);

        // 读取数据到buffer中
        int read = fileChannel.read(byteBuffer);
        while (read != -1) {

            System.out.println("读取了》》" + read);

            // The limit is set to the current position and then the position is set to zero.
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println((char) byteBuffer.get());
            }
            byteBuffer.clear();
            read = fileChannel.read(byteBuffer);
        }
        aFile.close();
        System.out.println("结束了！！！");
    }


}
