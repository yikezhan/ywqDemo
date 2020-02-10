package com.yuwuquan.demo.nio.fakenio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1、伪NIO，与BIO不同之处在于服务端用线程池处理请求。
 * 2、加了线程池后，不会撑爆服务器，但是会阻塞很严重，客户端可能要很久才能收到请求。
 */
public class FakeNIOService {
    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8011);
            Socket socket = null;
            ExecutorService executorService = Executors.newFixedThreadPool(100);
            while (true){
                socket = server.accept();//不断监听该端口数据
                executorService.execute(new TimeServerHandlePool(socket));//线程池处理当前客户端请求
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(server != null){
                server.close();
                server = null;
            }
        }
    }
}

class TimeServerHandlePool implements Runnable{
    private Socket socket;
    public TimeServerHandlePool(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
//            Thread.sleep(200000);//打开测试客户端是否是阻塞在那的
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String boby = null;
            while (true){
                boby = in.readLine();
                if(boby == null){
                    break;
                }
                currentTime = "Query time order".equalsIgnoreCase(boby)? (new Date()).toString() : "Bad order";
                out.println(currentTime);
            }
            Thread.sleep(2000);//打开测试线程过多时服务崩溃的效果
        } catch (Exception e) {
            if(in != null){
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if(out != null){
                out.close();
            }
            if(this.socket != null){
                try {
                    this.socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}
