package com.github.greekpanda.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
 * *
 *
 * @author GreekPanda
 * @version 1.0
 * @date 2020/6/8 0008 上午 11:52
 */
public class TestBlockingNIO {
    private static final String srcFile = "D:/BaiduNetdiskDownload/hanghaiwan.mkv";
    private static final String dstFile1 = "d:/1.mkv";

    private static final String host = "127.0.0.1";
    private static final int port = 19999;

    @Test
    public void client() throws IOException {

        //1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        FileChannel fileChannel = FileChannel.open(Paths.get(srcFile), StandardOpenOption.READ);

        //2. 分配指定大小的缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //3. 读取本地文件
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

        //2. 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(port));

        //3. 获取客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        //4. 分配缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //5. 接受客户端数据并保存本地
        while (socketChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        socketChannel.close();
        fileChannel.close();
        serverSocketChannel.close();
    }
}
