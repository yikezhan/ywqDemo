package com.yuwuquan.demo.jvmtool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * 用于替换System的输出，将测试类中每次System.out的内容输出到字节数组流中，最后一次性输出到页面
 */
public class HackSystem {
    public final static InputStream in=System.in;
    private static ByteArrayOutputStream buffer=new ByteArrayOutputStream();
    public static final PrintStream out=new PrintStream(buffer);
    public static final PrintStream err=out;
    public static  String getBuffer(){
        return buffer.toString();
    }
    public static void clearBuffer(){
        buffer.reset();
    }
}