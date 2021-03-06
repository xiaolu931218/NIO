package powernode;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TestFileChannel {

    /**
     * 测试FileChannel的map方法
     * 将in。txt中的内容写到out.txt文件中
     */
    @Test
    public void test01() throws Exception {
        File inFile = new File("F:\\tmp\\in.txt");
        File outFile = new File("F:\\tmp\\out.txt");
        FileChannel inChannel = new FileInputStream(inFile).getChannel();
        MappedByteBuffer mappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFile.length());
        FileChannel outChannel = new FileOutputStream(outFile).getChannel();
        outChannel.write(mappedByteBuffer);
    }

    /**
     * RandomAccessFile获得的Channel是双向的，
     * 将out.txt中的内容写到out.txt文件的结尾处
     */
    @Test
    public void test02() {

        File outFile = new File("F:\\tmp\\out.txt");
        try (
                FileChannel channel = new RandomAccessFile(outFile,"rw").getChannel();
        ){
            // 把Channel中的数据映射到到虚拟内存中
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, outFile.length());
            // 设置channel的position属性
            channel.position(outFile.length());
            channel.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缓冲区大小固定时复制文件
     * 将in.txt中的内容写到out.txt文件中
     */
    @Test
    public void test03() {

        File inFile = new File("F:\\tmp\\in.txt");
        File outFile = new File("F:\\tmp\\out.txt");
        try (
                FileChannel inChannel = new FileInputStream(inFile).getChannel();
                FileChannel outChannel = new FileOutputStream(outFile).getChannel();
        ){
            ByteBuffer byteBuffer = ByteBuffer.allocate(2);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 把文件批量的从一个位置复制到另一个位置，可以使用channel传输到另一个channel，不需要使用buffer
     * 将in.txt中的内容写到out.txt文件中
     */
    @Test
    public void test04() {

        File inFile = new File("F:\\tmp\\in.txt");
        File outFile = new File("F:\\tmp\\out.txt");
        try (
                FileChannel inChannel = new FileInputStream(inFile).getChannel();
                FileChannel outChannel = new FileOutputStream(outFile).getChannel();
        ){
//            // 把inChannel的内容传输到outChannel
//            inChannel.transferTo(0, inFile.length(), outChannel);
            // 把inChannel的内容传输到outChannel
            outChannel.transferFrom(inChannel, 0, inFile.length());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 通过gather方式写入文件
     */
    @Test
    public void test05() {

        File inFile = new File("F:\\tmp\\in.txt");
        File outFile = new File("F:\\tmp\\out.txt");
        try (
                FileChannel inChannel = new FileInputStream(inFile).getChannel();
                FileChannel outChannel = new FileOutputStream(outFile).getChannel();
        ){
            ByteBuffer pathBuffer = ByteBuffer.allocate(1024);
            ByteBuffer typeBuffer = ByteBuffer.allocate(48);
            ByteBuffer contentBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            // 字节数组进行聚集
            ByteBuffer[] byteBuffers = {pathBuffer, typeBuffer, contentBuffer};

            pathBuffer.put((inFile.getAbsolutePath() + "\r\n").getBytes(StandardCharsets.UTF_8));
            typeBuffer.put((URLConnection.guessContentTypeFromName(inFile.getAbsolutePath()) + "\r\n").getBytes(StandardCharsets.UTF_8));
            // 不返回0就一直写
            while (outChannel.write(byteBuffers) > 0) {}


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
