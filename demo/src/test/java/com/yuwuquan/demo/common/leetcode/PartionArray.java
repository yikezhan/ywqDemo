package com.yuwuquan.demo.common.leetcode;

/**
 * 给你一个整数数组 A，只有可以将其划分为三个和相等的非空部分时才返回 true，否则返回 false。
 * 形式上，如果可以找出索引 i+1 < j 且满足 (A[0] + A[1] + ... + A[i] == A[i+1] + A[i+2] + ... + A[j-1] == A[j] + A[j-1] + ... + A[A.length - 1]) 
 * 就可以将数组三等分。
 *
 */
public class PartionArray {
    public boolean canThreePartsEqualSum(int[] A) {
        if(A.length < 3) return false;
        int sum = 0;
        for(int i=0;i<A.length;i++){
            sum += A[i];
        }
        if(sum%3 != 0) return false;
        int v = sum/3;
        int val1 = 0;
        int val2 = 0;
        for(int i = 0;i<A.length;i++){
            val1 += A[i];
            if(val1 == v){
                for(int j=i+1;j<A.length;j++){
                    val2 += A[j];
                    if(val2 == v && j!=A.length-1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] A={1,-1,1,-1};
        System.out.println(new PartionArray().canThreePartsEqualSum(A));
    }
}
