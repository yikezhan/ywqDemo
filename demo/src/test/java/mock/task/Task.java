package mock.task;

import lombok.Data;
import mock.user.User;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Task {
    private long id;
    private String name;
    private User createUser;
    private String question;
    private String answer;
    private AtomicLong needPerson;
    private AtomicLong totalMoney;
    private AtomicLong allocationMoney;
    private static long perMoney = 1l;//每份价格

    public void finishTask(){
        allocationMoney.getAndAdd(perMoney);
        needPerson.getAndAdd(-1);
    }
    public Long getFinishTaskMoney(){//分配策略
        return perMoney;
    }

    public Task(long id, String name, User createUser, String question, String answer, AtomicLong needPerson, AtomicLong totalMoney) {
        this.id = id;
        this.name = name;
        this.createUser = createUser;
        this.question = question;
        this.answer = answer;
        this.needPerson = needPerson;
        this.totalMoney = totalMoney;
        this.allocationMoney = new  AtomicLong(0);
    }
}
