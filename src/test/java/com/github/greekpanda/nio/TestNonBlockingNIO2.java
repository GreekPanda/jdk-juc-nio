package com.github.greekpanda.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * * 一、使用 NIO 完成网络通信的三个核心：
 * *
 * * 1. 通道（Channel）：负责连接
 * *
 * * 	   java.nio.channels.Channel 接口：
 * * 			|--SelectableChannel
 * * 				|--SocketChannel
 * * 				|--ServerSocketChannel
 * * 				|--DatagramChannel
 * *
 * * 				|--Pipe.SinkChannel
 * * 				|--Pipe.SourceChannel
 * *
 * * 2. 缓冲区（Buffer）：负责数据的存取
 * *
 * * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 下午 12:15
 */
public class TestNonBlockingNIO2 {
    private static final String srcFile = "D:/BaiduNetdiskDownload/hanghaiwan.mkv";
    private static final String dstFile1 = "d:/1.mkv";

    private static final String host = "127.0.0.1";
    private static final int port = 19998;


    @Test
    public void client() throws IOException {

        //1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        FileChannel fileChannel = FileChannel.open(Paths.get(srcFile), StandardOpenOption.READ);

        //2. 配置为非阻塞模式
        socketChannel.configureBlocking(false);

        //3. 分配指定大小的缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //4. 读取本地文件
        while (fileChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        fileChannel.close();
        socketChannel.close();
    }

    @Test
    public void server() throws IOException {
        //1. 打开通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        FileChannel fileChannel = FileChannel.open(Paths.get(dstFile1),
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);

        //2. 配置为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //3. 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(port));

        //4. 获取选择器
        Selector selector = Selector.open();

        //5. 配置选择器选项
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6. 轮询获取选择器上的就绪事件
        while (selector.select() > 0) {
            //7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                //8. 获取准备“就绪”的是事件
                SelectionKey sk = iterator.next();
                //9. 判断具体是什么事件准备就绪
                if (sk.isAcceptable()) {
                    //10. 若“接收就绪”，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //11. 切换非阻塞模式
                    socketChannel.configureBlocking(false);

                    //12. 将该通道注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);

                } else if (sk.isReadable()) {
                    //13. 获取当前选择器上“读就绪”状态的通道
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
                    //14. 读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        //System.out.println(new String(byteBuffer.array(), 0, len));
                        fileChannel.write(byteBuffer);
                        byteBuffer.clear();
                    }
                }
                //15. 取消选择键 SelectionKey
                iterator.remove();
            }
        }

    }
}
