package com.yuwuquan.demo.nio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 传统的BIO通信模型。
 * 缺点：每有一个新的客户端请求，必须创建一个新的线程处理新接入的客户端链路。一个线程只能处理一个客户端链接。
 */
public class BIOService {
    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8011);
            Socket socket = null;
            while (true){
                socket = server.accept();//不断监听该端口数据
                new Thread(new TimeServerHandle(socket)).start();//启动一个新的线程处理当前客户端请求
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
class TimeServerHandle implements Runnable{
    private Socket socket;
    public TimeServerHandle(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
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
            //Thread.sleep(10000);//打开测试线程过多时服务崩溃的效果
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
