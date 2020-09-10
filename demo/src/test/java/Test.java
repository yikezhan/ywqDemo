import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

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
        A a = new A("ywq",12);
        B b = JSONObject.parseObject(JSON.toJSONString(a), B.class);
        System.out.println(b.toString());
    }
}
