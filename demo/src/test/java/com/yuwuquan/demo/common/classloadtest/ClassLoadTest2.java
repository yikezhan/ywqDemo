package com.yuwuquan.demo.common.classloadtest;

import java.io.IOException;
import java.io.InputStream;

/**
 * 只有在父类加载器加载失败时才会使用自定义的加载器。
 */
public class ClassLoadTest2 {
    public static void main(String[] args) throws Exception{
        ClassLoader myLoad = new ClassLoader() {//这里正常情况不会调用到这个加载
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(is == null){
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b , 0 ,b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };
        Object obj = myLoad.loadClass("com.yuwuquan.demo.common.classloadtest.ClassLoadTest2").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoadTest2);//true
    }
}
