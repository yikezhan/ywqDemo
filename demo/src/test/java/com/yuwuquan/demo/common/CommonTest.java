package com.yuwuquan.demo.common;

import java.util.*;
import java.util.stream.Collectors;

public class CommonTest {
    String s;
    public String getS(){
        return s;
    }
    public static void main(String[] args) {
        List<CommonTest> list = new ArrayList<>();
        CommonTest c = new CommonTest();c.s="s";
        list.add(new CommonTest());
        String s = list.stream().map(CommonTest::getS).filter(Objects::nonNull).collect(Collectors.joining(","));
        System.out.println(s);
    }
    public void gameOfLife(int[][] board) {
        int[][] res = new int[board.length][board[0].length];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                int aliveNum = check(board,i-1,j-1) + check(board,i-1,j)
                        + check(board,i-1,j+1) + check(board,i,j-1)
                        + check(board,i,j+1) + check(board,i+1,j-1)
                        + check(board,i+1,j) + check(board,i+1,j+1);
                if(aliveNum<2) res[i][j] = 0;
                if(aliveNum == 2 && board[i][j]==1) res[i][j]=1;
                if(aliveNum == 3) res[i][j]=1;
                if(aliveNum>3) res[i][j] = 0;
            }
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                board[i][j] = res[i][j];
            }
        }

    }
    public int check(int[][] board, int i, int j){//活细胞则返回1，其他返回0
        if(i>=0 && i<board.length && j>=0 && j<board[0].length){
            return board[i][j];
        }else{
            return 0;
        }
    }
}
