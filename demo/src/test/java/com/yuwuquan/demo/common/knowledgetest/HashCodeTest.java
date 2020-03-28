package com.yuwuquan.demo.common.knowledgetest;

import java.util.HashMap;
import java.util.Map;

public class HashCodeTest {
    private Integer id;
    private String name;
    private Integer age;
    public HashCodeTest(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Override
    public int hashCode() {//如果不重新hashCode的话，测试里面hashMap的put操作会将equals相等的值还是作为一个新值塞进去。
        return id.hashCode() + name.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HashCodeTest){
            HashCodeTest h = ((HashCodeTest)obj);
            if(h.id == this.id && h.name.equals(this.name))
                return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return this.name;
    }

    public static void main(String[] args) {
        HashCodeTest ywqChild = new HashCodeTest(1,"ywq", 6);
        HashCodeTest ywqAdult = new HashCodeTest(1,"ywq", 19);
        //重写了equals方法，所以成人ywq和儿童ywq是通用一个人
        System.out.println(ywqChild.equals(ywqAdult));//true

        HashMap<HashCodeTest,Integer> hashMap = new HashMap<>();
        hashMap.put(ywqChild,1);
        hashMap.put(ywqAdult,2);
        //由于重写了hashcode方法，所以再put操作时，会视这两个为同一个人
        // 如果不重写，那么还没到equals，在hashcode比较时就是不一样的，就会返回false，下面就会输出两条记录了
        for(Map.Entry entry : hashMap.entrySet()){//ywq:2
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}
