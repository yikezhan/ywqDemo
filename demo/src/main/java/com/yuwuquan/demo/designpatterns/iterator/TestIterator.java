package com.yuwuquan.demo.designpatterns.iterator;

import java.util.ArrayList;
import java.util.HashMap;

public class TestIterator {
    public static void main(String[] args) {
        Books<Integer,String> books = new Books<>();
        books.put(1,"鲁滨逊漂流记");
        books.put(2,"地心游记");
        //第一种遍历方法
        Books.KeyIterator iterator = books.new KeyIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        //第二种遍历方法，因为实现了Iterable接口的语法糖形式
        for (Integer key : books) {
            System.out.println(key);
        }
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1,"a");
        map.put(2,"b");
        map.put(3,"c");
        for(Integer key : map.keySet()){
            System.out.println(map.get(key));
        }
    }
}
