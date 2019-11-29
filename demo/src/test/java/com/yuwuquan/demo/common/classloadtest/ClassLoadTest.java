package com.yuwuquan.demo.common.classloadtest;

import java.io.InputStream;

/**
 * 重写类加载器，使用时会调用loadClass。
 */
public class ClassLoadTest {
    public static void main(String[] args) throws Exception{
        ClassLoader myLoad = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(is == null){
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b , 0 ,b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException();
                }
            }
        };
        Object obj = myLoad.loadClass("com.yuwuquan.demo.common.classloadtest.ClassLoadTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoadTest);
    }
}
