package com.yuwuquan.demo.common;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        try {
            Test test2 = (Test)Test.class.getClassLoader().loadClass("com.yuwuquan.demo.common.Test").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Test test3 = (Test) Class.forName("com.yuwuquan.demo.common.Test").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
