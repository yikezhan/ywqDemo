package com.yuwuquan.demo.common.multhreadtest.waitnotifytest;

/**
 * 发件人应该将数据分组发送到接收器
 * 接受者不能处理数据包，直到发送者将发送的数据包发送完成
 * 同样，发送方不得尝试发送另一个数据包，除非接收方已处理过上一个数据包
 */
class Data {
    private String packet;
    private boolean transfer = true;
    public synchronized void send(String packet) {
        while (!transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
            }
        }
        transfer = false;
        this.packet = packet;
        notify();
    }

    public synchronized String receive() {
        while (transfer) {
            try {
                wait();
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();//不发送异常离开该线程的办法
            }
        }
        transfer = true;
        notify();
        return packet;
    }
}
public class SendReceived {
    private static Data data = new Data();
    public static void main(String[] args) {
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                String packets[] = {
                        "First packet",
                        "Second packet",
                        "Third packet",
                        "Fourth packet",
                        "End"
                };
                for (String packet : packets) {
                    data.send(packet);
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e)  {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        Thread receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                for(String receivedMessage = data.receive();  !"End".equals(receivedMessage); receivedMessage = data.receive()) {
                    System.out.println(receivedMessage);
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        sender.start();
        receiver.start();
    }
}
