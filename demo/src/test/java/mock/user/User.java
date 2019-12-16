package mock.user;

import lombok.Getter;
import mock.queue.Queue;
import mock.task.Task;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class User {
    private static final int  MAXSENDTASK = 1;//最大发布任务值
    private static final int  MAXRECIVEDTASK = 3;//最大接收任务值
    private ArrayList<Task> receivedTask= new ArrayList<>(MAXRECIVEDTASK);//接收的任务id
    private long id;
    @Getter
    private String name;
    private int sendTaskSize = 0;//发布任务数
    private int credit;//信誉值
    @Getter
    private AtomicLong money;
    private int receivedTaskSize = 0;//当前已接任务数。最大值为3

    public void addMoney(long addMoney){
        money.getAndAdd(addMoney);
    }
    public boolean removeTaskIfExist(Task task) {
        if(receivedTask.contains(task)){
            receivedTask.remove(task);
            return true;
        }
        return false;
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
        this.sendTaskSize = 0;
        this.credit = 100;
        this.money = new AtomicLong(0);
        this.receivedTaskSize = 0;
    }
    public boolean reveivedTask(Queue queue, Task task){
        if(receivedTaskSize >= MAXRECIVEDTASK || receivedTask.contains(task)){
            return false;//接收任务到了上限
        }else{
            receivedTask.add(task);
            ++receivedTaskSize;
            queue.push(this);
            return true;
        }
    }
    public boolean sendTask(Queue queue, Task task){
        if(sendTaskSize >= MAXSENDTASK){
            return false;//接收任务到了上限
        }else{
            ++sendTaskSize;
            return true;
        }
    }
}
