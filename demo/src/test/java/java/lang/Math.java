package java.lang;

import java.io.InputStream;

/**
 * 测试双亲委托模型，装载自定义Math失败情景。
 */
public class Math extends ClassLoader{
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
    public static void main(String[] args) {
        System.out.println("ss");
    }
}
