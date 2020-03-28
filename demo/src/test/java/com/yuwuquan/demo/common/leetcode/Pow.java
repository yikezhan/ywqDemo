package com.yuwuquan.demo.common.leetcode;

/**
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 *
 *         示例 1:
 *
 *         输入: 2.00000, 10
 *         输出: 1024.00000
 */
public class Pow {
    public double myPow(double x, int n) {
        double res = 1;
        for(int i = n; i !=0; i = i/2){
            if(i%2 != 0)
                res *= x;
            x *= x;
        }
        return  n>0 ? res : 1/res;
    }
    public static void main(String[] args) {
        System.out.println(new Pow().myPow(2.00000,-2147483648));
    }
}
