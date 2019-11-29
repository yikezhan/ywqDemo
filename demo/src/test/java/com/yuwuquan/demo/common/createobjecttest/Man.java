package com.yuwuquan.demo.common.createobjecttest;

import java.io.Serializable;

public class Man implements Cloneable, Serializable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}