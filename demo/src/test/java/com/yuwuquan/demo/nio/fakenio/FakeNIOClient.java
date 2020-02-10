package com.yuwuquan.demo.nio.fakenio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FakeNIOClient implements Runnable{
    Integer v;
    FakeNIOClient(Integer v){
        this.v = v;
    }
    @Override
    public void run() {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String resp = null;
        try {
            socket = new Socket("127.0.0.1",8011);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("query time order");//发送指令
            resp = in.readLine();//获取服务端的返回结果。是阻塞在这的，只有服务端把结果返回才会继续执行
            System.out.println("Thread "+v+",Now is : " + resp);
            out.println("query your dad");//发送指令
            resp = in.readLine();//获取服务端的返回结果
            System.out.println("Thread "+v+",Your dad : " + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                socket = null;
            }
        }
    }

    public static void main(String[] args) {
        for (int i=0; i<1; i++){//模拟50个客户端访问
            Thread thread = new Thread(new FakeNIOClient(i));
            thread.start();
        }
    }
}
