package com.yuwuquan.demo.common;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * 创建一个对象的几种方法
 */
public class CreateObjectTest {
    public static void main(String[] args) {
        //第一种
        Man m1 = new Man();
        //第二种，反射，直接使用newInstance
        Class c1 = null;
        try {
            c1 = Class.forName("com.yuwuquan.demo.common.Man");
            Man m2 = (Man)c1.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //第三种，反射，使用getConstructor
        Class c2 = null;
        try {
            c2 = Class.forName("com.yuwuquan.demo.common.Man");
            Man m3 = (Man)c2.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //第四种，要实现实现Cloneable接口，提升clone()方法可见度。（原来是product级别的）
        try {
            Man m4 = (Man)m1.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //第五种，序列化和反序列化，要实现实现Serializable接口
        try {
            //准备一个文件用于存储该对象的信息
            File f = new File("m1.obj");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            //序列化对象，写入到磁盘中
            oos.writeObject(m1);
            //反序列化对象
            Man m5 = (Man)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
