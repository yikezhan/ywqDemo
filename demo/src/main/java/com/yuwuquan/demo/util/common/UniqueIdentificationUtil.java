package com.yuwuquan.demo.util.common;

import java.util.Date;

public class UniqueIdentificationUtil {
    private final static String USER = "bingo";
    public static String getNewUserUnique(){
        return ("bingo_" + (new Date()).getTime() +Math.random()).replace(".","_");
    }

    public static void main(String[] args) {
        System.out.println(getNewUserUnique());
        System.out.println(getNewUserUnique());
    }
}
