package mock;

import mock.queue.Queue;
import mock.task.Task;
import mock.user.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class MockTest {
    private static ExecutorService executor = Executors.newFixedThreadPool(30);
    public static void main(String[] args) throws InterruptedException {
        //任务队列
        Queue<Task> Task_Index = new Queue<>();
        //用户队列
        Queue<User> Customer_filter_index = new Queue<>();
        Queue<User> Customer_common_index = new Queue<>();
        Queue<User> Customer_lower_index = new Queue<>();

        /**
         * 发布两个任务
         */
        //ywq发布任务
        User ywq = new User(-1,"ywq");
        Task task1 = new Task(1,"盖楼活动",ywq, "his name？", "wq",new AtomicLong(100), new AtomicLong(100));
        ywq.sendTask(Task_Index, task1);
        Task_Index.push(task1);
        //wb发布任务
        User wb = new User(-2,"wb");
        Task task2 = new Task(2,"外卖红包",wb, "her boy name？", "wq",new AtomicLong(100), new AtomicLong(100));
        wb.sendTask(Task_Index, task2);
        Task_Index.push(task2);

        /**
         * 不断注册路人
         */
        executor.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=1; i>0; i++){
                    User passer1 = new User(i,"passer"+i);
                    if(i%3 == 0){
                        passer1.reveivedTask(Customer_common_index, task1);
                    }else{
                        passer1.reveivedTask(Customer_common_index, task2);
                    }
                    if(i>100){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread.sleep(1000);
        /**
         * 不断消费任务
         */
        while(1==1){
            Task task = Task_Index.pop();
            if(task == null){//队列中没任务了，停10秒
                Thread.sleep(10000);
                continue;
            }
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    while(task.getNeedPerson().intValue() > 0){
                        User user = Customer_common_index.pop();
                        if(user != null && user.removeTaskIfExist(task)){
                            String userAnswer = "wq";
                            if(task.getAnswer().equals(userAnswer)){
                                task.finishTask();
                                user.addMoney(task.getFinishTaskMoney());
                                if(task.getNeedPerson().intValue()>0){
                                    Task_Index.push(task);
                                }
                                System.out.println(user.getName() +":"+user.getMoney());
                            }
                        }
                    }
                }
            });
        }
    }
}
