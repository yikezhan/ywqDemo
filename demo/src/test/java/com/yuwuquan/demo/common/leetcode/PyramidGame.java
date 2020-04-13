package com.yuwuquan.demo.common.leetcode;

import java.util.Stack;
//叠罗汉问题，上一个人只能比下一个轻和矮，求最多能叠几个
public class PyramidGame {
    public static void main(String[] args) {
        int[] h = {1,2,4,4,5};
        int[] w = {2,3,4,5,4};
//        int[] h = {1,2,3,4,5};
//        int[] w = {5,4,3,2,1};
        System.out.println(new PyramidGame().bestSeqAtIndex(h,w));
    }
    public int bestSeqAtIndex(int[] height, int[] weight) {
        if(height == null || weight == null) return 0;
        int length = height.length;
        int[][] hw = new int[length][2];
        for(int i=0;i<length;i++){
            hw[i][0] = height[i];
            hw[i][1] = weight[i];
        }
        quicksort(hw,0,length-1,0);//根据height排序
        int MAX1 = maxAddDeque(hw,1);
        quicksort(hw,0,length-1,1);//根据weight排序
        int MAX2 = maxAddDeque(hw,0);
        return MAX1>MAX2?MAX1:MAX2;
    }
    public void quicksort(int[][] nums,int start,int end,int tag) {
        while(end <= start) return;
        int index = start;
        int val = nums[index][tag];
        int val2 = nums[index][PyramidGame.abs(1-tag)];
        int left = start;
        int right = end;
        while(left<right){
            while((val<nums[right][tag] || (val==nums[right][tag] && val2>nums[right][PyramidGame.abs(1-tag)])) && right>index) right--;
            if(left>=right) break;
            swap(nums,index,right);
            index = right;
            left++;
            while((val>nums[left][tag] || (val==nums[left][tag] && val2<nums[left][PyramidGame.abs(1-tag)])) && left<index) left++;
            if(left>=right) break;
            swap(nums,index,left);
            index = left;
            right--;
        }
        quicksort(nums,start,index-1,tag);
        quicksort(nums,index+1,end,tag);
    }
    public void swap(int[][] nums,int i,int j){
        int tmp = nums[i][0];
        nums[i][0] = nums[j][0];
        nums[j][0] = tmp;
        tmp = nums[i][1];
        nums[i][1] = nums[j][1];
        nums[j][1] = tmp;
    }
    int maxAddDeque(final int[][] nums,final int tag){//LIS算法，最长递增子序列,注意这里重复的数据要去重
        Stack<Integer> stack = new Stack<>();
        for(int i =0; i<nums.length; i++){
            Stack<Integer> stack2 = new Stack<>();
            if(stack.size()==0){
                stack.push(nums[i][tag]);
                continue;
            }
            for(int val = stack.peek();val>=nums[i][tag] && stack.size() != 0;){
                stack2.push(stack.pop());
                if(stack.size()==0) break;
                else val = stack.peek();
            }
            stack.push(nums[i][tag]);
            if(stack2.size()!=0){
                for(stack2.pop();stack2.size()!=0;){
                    stack.push(stack2.pop());
                }
            }
        }
        return stack.size();
    }
    public static int abs(int a) {
        return (a < 0) ? -a : a;
    }
}
