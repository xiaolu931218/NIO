package powernode;

import org.junit.Test;

import java.nio.CharBuffer;
import java.util.Arrays;

public class Main {

    @Test
    public void test01() {
        CharBuffer charBuffer = CharBuffer.allocate(10);
        charBuffer.put("零一二三四五六七八九");
        // 容量:10
        System.out.println(charBuffer.capacity());
        // 上限:10
        System.out.println(charBuffer.limit());
        // 当前光标位置:8
        System.out.println(charBuffer.position());
        // get一个空字符
//        System.out.println(charBuffer.get());
        // 翻转，从写模式转到读模式
        charBuffer.flip();
        System.out.println("=======");

        while (charBuffer.hasRemaining()) {
            System.out.println("while-------");
            // 容量大小不变10
            System.out.println("capacity：" + charBuffer.capacity());
            // 上限大小不变
            System.out.println("limit：" + charBuffer.limit());
            // 读的位置，从0开始，依次加1
            System.out.println("get前的position：" + charBuffer.position());
            // 读取一个字符，最后依次读取的是一个空字符
            System.out.println("get：" + charBuffer.get());
            System.out.println("get后的position：" + charBuffer.position());
            if (charBuffer.position() == 5) {
                // mark方法标记当前位置，使用reset方法将position设置为mark标记的位置
                charBuffer.mark();
            }
        }
        // 使用reset方法将position设置为mark标记的位置
        charBuffer.reset();
        System.out.println("reset后position》》》》》" + charBuffer.position());
        while (charBuffer.hasRemaining()) {
            System.out.println("while-------");
            // 容量大小不变10
            System.out.println("capacity：" + charBuffer.capacity());
            // 上限大小不变
            System.out.println("limit：" + charBuffer.limit());
            // 读的位置，从0开始，依次加1
            System.out.println("get前的position：" + charBuffer.position());
            // 读取一个字符，最后依次读取的是一个空字符
            System.out.println("get：" + charBuffer.get());
            System.out.println("get后的position：" + charBuffer.position());
        }


    }
    @Test
    public void test02() {
        CharBuffer charBuffer = CharBuffer.allocate(10);
        charBuffer.put("零一二三四五六七八九");
        charBuffer.flip();
        System.out.println("buffer>>" + charBuffer);
        char[] cArray = new char[6];
        // 将buffer中的数据读到数组中，返回的是剩余的数据的buffer
        CharBuffer remainBuffer = charBuffer.get(cArray);
        System.out.println("buffer>>" + charBuffer);
        System.out.println("remainBuffer>>" + remainBuffer);
        System.out.println("cArray>>" + Arrays.toString(cArray));
//        charBuffer.get(cArray); // 当缓冲区的数据不足以填满数组时，报java.nio.BufferUnderflowException


    }

    /**
     * 创建缓冲区的方式
     * 这两种都是间接缓冲区
     */
    @Test
    public void test03() {
        // 第一种、分配操作
        CharBuffer buffer1 = CharBuffer.allocate(10);
        buffer1.put("123");
        buffer1.flip();
        System.out.println("buffer1>>" + buffer1);

        // 第二种、包装方法
        char[] chars = new char[10];
        chars[0] = 'a';

        CharBuffer charBuffer = CharBuffer.wrap(chars);
        chars[1] = 'b';
        System.out.println("buffer>>" + charBuffer);
        System.out.println("chars>>" + Arrays.toString(chars));
        charBuffer.put("c");
        charBuffer.flip();
        System.out.println("after put c==================" );
        System.out.println("buffer>>" + charBuffer);
        System.out.println("chars>>" + Arrays.toString(chars));
        // 判断是否有备份数组
        System.out.println("备份数组>>" + Arrays.toString(charBuffer.array()));



    }
}
