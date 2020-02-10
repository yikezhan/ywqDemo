package com.yuwuquan.demo.nio.nio;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用NIO，非阻塞，使用复杂。不推荐，直接使用netty更好。
 */
public class NIOServer {
    public static void main(String[] args) {
        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(8011);
        new Thread(multiplexerTimeServer,"NIO test-001:").start();
    }
}
class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;
    public MultiplexerTimeServer(int port){
        try {
            //1、打开serverSocketChannel,用于监听客户端连接，是所有客户端连接的父管道。
            serverSocketChannel = ServerSocketChannel.open();
            //2、设置为非阻塞，绑定监听的地址端口
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //3、创建Reactor线程，创建多路复用，并启动线程
            selector = Selector.open();
            /**
             * SelectionKey.OP_ACCEPT —— 接收连接进行事件，表示服务器监听到了客户连接，那么服务器可以接收这个连接了
             *
             * SelectionKey.OP_CONNECT —— 连接就绪事件，表示客户与服务器的连接已经建立成功
             *
             * SelectionKey.OP_READ  —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
             *
             * SelectionKey.OP_WRITE —— 写就绪事件，表示已经可以向通道写数据了（通道目前可以用于写操作）
             */
            //4、将serverSocketChannel注册到selector，监听ACCEPT事件。
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {//如果初始化失败（如端口被占用，则退出），
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void stop(){
        this.stop = true;
    }
    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);//休眠1秒
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();//获取所有处于“就绪”状态的Channel的key集合。
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                SelectionKey key = null;
                while(iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try{
                        handleInput(key);//处理处于活跃状态的SelectionKey对象
                    } catch (Exception e){
                        if( key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //多路复用器关闭后，所有注册在上面的Channel 和 Pipe等资源都会被自动去注册并关闭，所有不需要重复释放资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //处理接入的请求消息
            if(key.isAcceptable()) {//相当于完成了TCP连接的三次握手，TCP物理链路正式建立
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();//从key中获取ServerSocketChannel
                SocketChannel sc = ssc.accept();//从ServerSocketChannel中获取SocketChannel。
                sc.configureBlocking(false);//设置SocketChannel为非阻塞
                sc.register(selector, SelectionKey.OP_READ);//将SocketChannel注册到selector，监听READ事件。

            }
            if(key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();//获取到SocketChannel
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);//开辟了1M的缓冲区大小
                int readBytes = sc.read(readBuffer);//从缓冲区读数据
                if (readBytes > 0){//读取到字节
                    readBuffer.flip();//将缓冲区当前的limit设置为position，position设置为0，用于后续对缓冲区的读取操作。
                    byte[] bytes = new byte[readBuffer.remaining()];//根据可读的字节数创建数组
                    readBuffer.get(bytes);//调用该方法，将缓存区的数据复制到了bytes中。
                    String body = new String(bytes, "UTF-8");//设置bytes的编码格式
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "Query time order".equalsIgnoreCase(body)? (new Date()).toString() : "Bad order";
                    doWrite(sc, currentTime);//将结果返回给客户端
                }else if(readBytes < 0){//未读取到字节
                    //对端链路关闭
                    key.cancel();
                    sc.close();
                }else{
                    //读到0字节，忽略
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (response != null && response.trim().length() >0){
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);//开辟缓冲区大小
            writeBuffer.put(bytes);//将字节数组复制到缓冲区
            writeBuffer.flip();//进行flip操作
            socketChannel.write(writeBuffer);//将缓存区的数据发送出去。（这里可能会出现写“半包”的操作，由于只做演示，暂时不考虑）
        }
    }
}
