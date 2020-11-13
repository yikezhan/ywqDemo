import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuwuquan.demo.util.common.DateUtil;
import lombok.Data;

import java.util.Date;

@Data
class A{
    private String name;
    private int age;

    public A(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

@Data
class B {
    private String name;
    private int age;
}
public class Test{
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(JSONObject.toJSONString(date));
    }
}
