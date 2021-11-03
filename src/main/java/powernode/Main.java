package powernode;

import org.junit.Test;

import java.nio.CharBuffer;

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
}
