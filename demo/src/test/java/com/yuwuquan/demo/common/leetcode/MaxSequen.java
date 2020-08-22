package com.yuwuquan.demo.common.leetcode;

public class MaxSequen {
    public static void main(String[] args) {
        char[][] m = new char[4][3];
        m[0][0]='1';m[0][1]='1';m[0][2]='0';m[0][3]='1';
        m[1][0]='1';m[1][1]='1';m[1][2]='0';m[1][3]='1';
        m[2][0]='1';m[2][1]='1';m[2][2]='1';m[2][3]='1';
    }
    public int maximalSquare(char[][] matrix) {
        if(matrix == null || matrix.length == 0) return 0;
        int xMax = matrix.length;
        int yMax = matrix[0].length;
        int max = 0; //当前最大正方形的边长
        for(int i=0; i<xMax; i++) {
            for(int j=0; j<yMax; j++) {
                max =  topLeft(matrix, i, j, xMax, yMax,max);
            }
        }
        return max*max;
    }
    int topLeft(char[][] matrix, int i, int j, int xMax, int yMax, int max){
        if(matrix[i][j] == '0') return max;
        max = max==0?1:max;
        if(i+max >= xMax || j+max >=yMax){
            return max;
        }
        boolean flag =false;
        for(int m=i;m<i+max;m++){//
            for(int n=j;n<i+max;n++){
                if(matrix[m][n] == '0'){
                    flag = true;
                    break;
                }
            }
        }
        if(flag)    //不满足
            return max;

        while(i+max<xMax && j+max<yMax){
            for(int k=j;k<=j+max;k++){
                if(matrix[i+max][k] == '0'){
                    return max;
                }
            }
            for(int k=i;k<=i+max;k++){
                if(matrix[k][j+max] == '0'){
                    return max;
                }
            }
            max++;
        }
        return max;
    }
}
