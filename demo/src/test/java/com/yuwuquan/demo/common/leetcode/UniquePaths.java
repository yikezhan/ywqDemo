package com.yuwuquan.demo.common.leetcode;

public class UniquePaths {
    public static void main(String[] args) {
        new UniquePaths().uniquePaths(3,2);
    }
    public int uniquePaths(int m, int n) {
        int[][] v = new int[m][n];
        return find(v,m-1,n-1);
    }

    private int find(int[][] v, int m, int n) {
        if(m==0 || n == 0) return 1;
        if(v[m][n] == 0)
            v[m][n] = find(v,m-1,n) + find(v,m,n-1);
        return v[m][n];
    }
}
