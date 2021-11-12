package powernode;

import org.junit.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

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
}
