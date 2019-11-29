package com.yuwuquan.demo.common.knowledgetest;

/**
 * 测试静态分派(重载)
 * 测试动态分派（重写）
 */
public class StaticAndDynamicOverrideTest {
    static abstract class Human{
        protected abstract void sayHello();
    }
    static class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("hello,man");
        }
    }
    static class Woman extends Human{
        @Override
        protected void sayHello() {
            System.out.println("hello,woman");
        }
    }
    static void sayHello(Human human){
        System.out.println("hello, human");
    }
    static void sayHello(Man man){
        System.out.println("hello, man");
    }
    static void sayHello(Woman woman){
        System.out.println("hello, woman");
    }
    public static void main(String[] args) {
        /**
         * new一个对象分两步，第一步Human man;在栈上有个这个唯一标识。第二步，在堆上new Man();
         */
        Human man = new Man();
        Human woman = new Woman();
        /**
         * 动态分派（重写），运行期可知
         */
        man.sayHello();//hello,man
        woman.sayHello();//hello,woman
        /**
         * 静态分派（重载），编译期可知。哈哈，看方法亮不亮都知道了
         */
        sayHello(man);//hello, human
    }
}
