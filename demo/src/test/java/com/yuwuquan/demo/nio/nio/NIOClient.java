package com.yuwuquan.demo.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public NIOClient(String host, int port) {//初始化NIO的多路复用器和SockeChannel
        this.host = host == null?"127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {//这部分如果连接失败了，需要重连，可以考虑放到下面循环里面去。
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    handleInput(key);//处理具体的活跃Socket的SelectionKey
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleInput(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isValid()){//可用，注销时只是把该值设为不可用，在下次使用时再真正删除。所以这里需要判断isValid
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            if(selectionKey.isConnectable()){//已连接
                if(sc.finishConnect()){//连接成功
                    sc.register(selector, SelectionKey.OP_READ);//注册SocketChannel到selector上，注册SelectionKey.OP_READ操作位
                    doWrite(socketChannel);//发消息给服务端
                }else{//连接失败
                    System.exit(1);//连接失败，进程退出
                }

            }
            if(selectionKey.isReadable()){//如果客户端接收到了服务端的应答请求，则可读
                ByteBuffer readBufffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBufffer);
                if(readBytes > 0){
                    readBufffer.flip();
                    byte[] bytes = new byte[readBufffer.remaining()];
                    readBufffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("Now is : " + body);
                    this.stop = true;
                }else if(readBytes < 0){
                    //对端链路关闭
                    selectionKey.cancel();
                    sc.close();
                }else{
                    //读到0字节，不做处理
                }
            }

        }
    }
    private void doConnect() throws IOException {
        //如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            socketChannel.register(selector, SelectionKey.OP_READ);//将socketChannel注册到selector上，注册SelectionKey.OP_READ
            doWrite(socketChannel);
        }else{
            //没有返回TCP握手消息，不一定表示连接失败，需要将socketChannel注册到selector上，注册SelectionKey.OP_CONNECT，
            //当服务端返回TCP syn-ack消息后，Selector就能够轮询到这个SocketChannel处于连接就绪状态
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] req = "Query time order".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
        if(!writeBuffer.hasRemaining()){//缓存消息全部发送完，则打印
            System.out.println("Second order 2 server succeed.");
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            new Thread(new NIOClient(null,8011)).start();
        }
    }
}
