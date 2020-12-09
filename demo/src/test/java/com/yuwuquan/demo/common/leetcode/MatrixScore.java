package com.yuwuquan.demo.common.leetcode;
//    有一个二维矩阵 A 其中每个元素的值为 0 或 1 。
//    移动是指选择任一行或列，并转换该行或列中的每一个值：将所有 0 都更改为 1，将所有 1 都更改为 0。
//    在做出任意次数的移动后，将该矩阵的每一行都按照二进制数来解释，矩阵的得分就是这些数字的总和。
//    返回尽可能高的分数。
//
//    示例：
//
//    输入：[[0,0,1,1],[1,0,1,0],[1,1,0,0]]
//    输出：39
//    解释：
//    转换为 [[1,1,1,1],[1,0,0,1],[1,1,1,1]]
//    0b1111 + 0b1001 + 0b1111 = 15 + 9 + 15 = 39


public class MatrixScore {
    public static void main(String[] args) {
        int[][] A = {{0,0,1,1},{1,0,1,0},{1,1,0,0}};
        System.out.println(new MatrixScore().matrixScore(A));
    }
    public int matrixScore(int[][] A) {
        int xLength = A[0].length;
        int yLength = A.length;
        for(int y=0;y<yLength;y++){
            if(A[y][0]==0){
                for(int x=0;x<xLength;x++){
                    A[y][x]=(A[y][x]+1)%2;
                }
            }
        }
        for(int x=1;x<xLength;x++){
            int zeroNum = 0;
            for(int y=0;y<yLength;y++){
                if(A[y][x]==0){
                    zeroNum++;
                }
            }
            if(zeroNum * 2 > yLength){
                for(int y=0;y<yLength;y++){
                    A[y][x]=(A[y][x]+1)%2;
                }
            }
        }
        print(A);
        return sum(A);
    }
    private int sum(int[][] A) {
        int sum=0;
        for(int i=0;i<A.length;i++){
            String s="";
            for(int j=0;j<A[0].length;j++){
                s +=A[i][j];
            }
            sum  += Integer.valueOf(s,2);
        }
        return sum;
    }
    private void print(int[][] A) {
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A[0].length;j++){
                System.out.print(A[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }

}
