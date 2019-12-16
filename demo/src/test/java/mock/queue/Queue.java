package mock.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自定义队列类
 * @param <T>
 */
public class Queue<T>{
    class Node<T>{
        T o;
        Node nextNode;
    }
    private Node<T> frontNode;
    private Node<T> rearNode;
    public Queue(){
    }
    public void push(Object o){
        synchronized(this){
            Node node = new Node();
            node.o = o;
            if(rearNode == null || frontNode == null){
                node.nextNode = rearNode;
                frontNode = node;
            }else{
                rearNode.nextNode = node;
            }
            rearNode = node;
        }
    }
    public T pop(){
        synchronized(this){
            if(frontNode == null){
                return null;
            }
            T t = frontNode.o;
            frontNode = frontNode.nextNode;
            return t;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> queue = new Queue<>();
        ExecutorService executor = Executors.newFixedThreadPool(20);
        for(int i=1; i< 100; i++){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    queue.push(1);
                    queue.push(2);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    queue.push(3);
                    System.out.println(queue.pop());
                    System.out.println(queue.pop());
                    System.out.println(queue.pop());
                }
            });
        }
        Thread.sleep(10000);
        System.out.println(queue.pop());
    }
}
