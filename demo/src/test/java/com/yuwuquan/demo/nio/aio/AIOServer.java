package com.yuwuquan.demo.nio.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * NIO2.0，非多路复用，真正的异步io接口。此处代码不全
 */
public class AIOServer {
    public static void main(String[] args) {

    }
}

class AsyncTimeServerHandler implements Runnable{

    private int port;
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void doAccept(){
//        asynchronousServerSocketChannel.accept(this, new ReadCompletionHandler());
    }
}

class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousServerSocketChannel channel;

    public ReadCompletionHandler(AsynchronousServerSocketChannel channel) {
        if(channel != null){
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        //...
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {

    }
}