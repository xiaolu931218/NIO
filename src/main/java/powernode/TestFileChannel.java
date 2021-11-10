package powernode;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
}
