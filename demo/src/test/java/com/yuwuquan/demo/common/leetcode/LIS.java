package com.yuwuquan.demo.common.leetcode;

import java.util.Stack;

//最长递增子序列
public class LIS {
    public static void main(String[] args) {
        int[] a = {10,9,2,5,3,4};
        System.out.println(new LIS().lengthOfLIS(a));
    }
    public int lengthOfLIS(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        for(int i =0; i<nums.length; i++){
            Stack<Integer> stack2 = new Stack<>();
            if(stack.size()==0){
                stack.push(nums[i]);
                continue;
            }
            for(int val = stack.peek();val>=nums[i] && stack.size() != 0;){
                stack2.push(stack.pop());
                if(stack.size()==0) break;
                else val = stack.peek();
            }
            stack.push(nums[i]);
            if(stack2.size()!=0){
                for(stack2.pop();stack2.size()!=0;){
                    stack.push(stack2.pop());
                }
            }
        }
        return stack.size();
    }
}
