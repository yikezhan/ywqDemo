package com.yuwuquan.demo.common.leetcode;
import java.util.*;
public class AIRun {
    public boolean robot(String command, int[][] obstacles, int x, int y) {
        char[] chars = command.toCharArray();
        int unum = 0;//几步y
        int rnum = 0;//几步x
        for(char c : chars){
            if(c == 'U') unum++;
            else rnum++;
        }
        int k = (x/rnum < y/unum) ? x/rnum : y/unum;//取小的那个倍数
        HashMap<Integer,HashSet<Integer>> map = new HashMap<>();//存储障碍物
        for(int i=0;i<obstacles.length;i++){
            if(x==obstacles[i][0] && y==obstacles[i][1]) return false;
            HashSet<Integer> set = map.get(obstacles[i][0]);
            if(set == null){
                set = new HashSet<Integer>();
                map.put(obstacles[i][0],set);
            }
            set.add(obstacles[i][1]);
        }

        int X = 0;
        int Y = 0;
        boolean flag = false;//标记能否到达目的地
        for(char c : chars){
            int re = check(map,k,rnum,unum,X,Y,x,y);
            if(re==0)
                return false;
            if(re == 2)//到达了目的地
                flag = true;
            if(c == 'U')
                Y++;
            if(c == 'R')
                X++;
        }
        int re = check(map,k,rnum,unum,X,Y,x,y);//最后一个格子还需要单独执行一次
        if(re==0) return false;
        if(re == 2) flag = true;
        return flag;
    }
    int check(HashMap<Integer,HashSet<Integer>> map,int k,int maxX,int maxY,int x,int y,int destX,int destY){
        for(int i=0;i<=k;i++){
            if(i*maxX+x>destX || i*maxY+y>destY) break;//超过目的地的节点不用判断是否会撞到
            if(map.get(i*maxX+x)!=null && map.get(i*maxX+x).contains(i*maxY+y))
                return 0;//撞到了
            if(i*maxX+x==destX && i*maxY+y==destY) return 2;//到目的地了
        }
        return 1;//没撞到
    }
}
