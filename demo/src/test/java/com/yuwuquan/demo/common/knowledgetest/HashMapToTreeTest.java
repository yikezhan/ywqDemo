package com.yuwuquan.demo.common.knowledgetest;

import java.util.HashMap;

/**
 * hashmap节点转为红黑树的测试及总结
 */
public class HashMapToTreeTest {
    static class MyHashMap {
        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    /**
     * 模拟hashmap冲突时的扩容
     * @param args
     */
    public static void main(String[] args) {
        HashMap<MyHashMap,Integer> my = new HashMap<>();
        my.put(new MyHashMap(),0);//第0个节点，放在node节点里，不占用链表位置
        my.put(new MyHashMap(),1);//链表当前个数为0，put后个数为1
        my.put(new MyHashMap(),2);//链表当前个数为1，put后个数为2
        my.put(new MyHashMap(),3);//链表当前个数为2，put后个数为3
        my.put(new MyHashMap(),4);//链表当前个数为3，put后个数为4
        my.put(new MyHashMap(),5);//链表当前个数为4，put后个数为5
        my.put(new MyHashMap(),6);//链表当前个数为5，put后个数为6
        my.put(new MyHashMap(),7);//链表当前个数为6，put后个数为7
        my.put(new MyHashMap(),8);//链表当前个数为7，可能发生红黑树转换,若不转换put后个数为8
        my.put(new MyHashMap(),9);//链表当前个数为8，可能发生红黑树转换,若不转换put后个数为9
        my.put(new MyHashMap(),10);//链表当前个数为9，必定发生红黑树转换
        //总结，加上node节点，最多开始挂第11个节点才可能发生红黑树转换
        //  if(当前个数>=8-1){
        //      if(数组长度<64){
        //          resize()
        //      else
        //          转为红黑树
        //      }
        // }
    }
}
