package com.github.greekpanda.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 ** 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 *  *
 *  * 二、通道的主要实现类
 *  * 	java.nio.channels.Channel 接口：
 *  * 		|--FileChannel
 *  * 		|--SocketChannel
 *  * 		|--ServerSocketChannel
 *  * 		|--DatagramChannel
 *  *
 *  * 三、获取通道
 *  * 1. Java 针对支持通道的类提供了 getChannel() 方法
 *  * 		本地 IO：
 *  * 		FileInputStream/FileOutputStream
 *  * 		RandomAccessFile
 *  *
 *  * 		网络IO：
 *  * 		Socket
 *  * 		ServerSocket
 *  * 		DatagramSocket
 *  *
 *  * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 *  * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 *  *
 *  * 四、通道之间的数据传输
 *  * transferFrom()
 *  * transferTo()
 *  *
 *  * 五、分散(Scatter)与聚集(Gather)
 *  * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 *  * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 *  *
 *  * 六、字符集：Charset
 *  * 编码：字符串 -> 字节数组
 *  * 解码：字节数组  -> 字符串
 *  *
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 上午 11:06
 */
public class TestChannel {

    private static final String srcFile = "D:\\BaiduNetdiskDownload\\hanghaiwan.mkv";
    private static final String dstFile1 = "d:/1.mkv";
    private static final String dstFile2 = "d:/2.mkv";

    //利用通道完成文件的复制（非直接缓冲区）
    @Test
    public void test1() {
        long start = System.currentTimeMillis();

        FileInputStream fis = null;
        FileOutputStream fos = null;

        FileChannel inChannel = null;
        FileChannel outChannel = null;

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(dstFile1);

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (inChannel != null) {
                try {
                    inChannel.close();
                    inChannel = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outChannel != null) {
                try {
                    outChannel.close();
                    outChannel = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    //使用直接缓冲区完成文件的复制(内存映射文件)
    @Test
    public void test2() {
        long start = System.currentTimeMillis();

        try {
            FileChannel inChannel = FileChannel.open(Paths.get(srcFile), StandardOpenOption.READ);
            FileChannel outChannel = FileChannel.open(Paths.get(dstFile2),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.READ,
                    StandardOpenOption.CREATE);

            //内存映射文件
            MappedByteBuffer inMappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            //直接对缓冲区进行数据的读写操作
            byte[] dst = new byte[inMappedBuffer.limit()];
            inMappedBuffer.get(dst);
            outMappedBuffer.put(dst);

            inChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    //通道之间的数据传输(直接缓冲区)
    @Test
    public void test3() throws IOException {

        long start = System.currentTimeMillis();

        FileChannel inChannel = FileChannel.open(Paths.get(srcFile), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(dstFile2),
                StandardOpenOption.WRITE,
                StandardOpenOption.READ,
                StandardOpenOption.CREATE);

        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    //分散和聚集
    @Test
    public void test4() throws IOException {

        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");

        //1. 获取通道
        FileChannel channel1 = raf1.getChannel();

        //2. 分配指定缓存区大小
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);

        //3. 分散读取
        ByteBuffer[] byteBuffers = {byteBuffer1, byteBuffer2};
        channel1.read(byteBuffers);

        for (ByteBuffer bb : byteBuffers) {
            bb.flip();
        }

        System.out.println(new String(byteBuffers[0].array(), 0, byteBuffers[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(byteBuffers[1].array(), 0, byteBuffers[1].limit()));

        //4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(byteBuffers);

    }

    @Test
    public void test5() {
        Map<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();

        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + " === " + entry.getValue());
        }

    }

    @Test
    public void test6() throws CharacterCodingException {

        Charset cs1 = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("我爱中国！");
        cBuf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 10; i++) {
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        System.out.println("------------------------------------------------------");

        Charset cs2 = Charset.forName("GBK");
        bBuf.flip();
        CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());
    }


}
