package com.yuwuquan.demo.common.leetcode;

import java.util.HashMap;
import java.util.Map;

public class MaxLengthSubString {
    public int lengthOfLongestSubstring(String s) {
        int max = 1;
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();
        int index = 1;
        int current = 1;
        if(s.equals("")) return 0;
        if(chars.length == 1) return 1;
        for (int i=0;i<chars.length;i++){
            map.put(chars[i],1);
            if(index<=i) index++;
            while(index < chars.length && map.get(chars[index]) == null){
                current ++;
                map.put(chars[index],1);
                index++;
            }
            map.put(chars[i],null);
            if (current > max) max = current;
            if(index > i+1) current--;
            if(index >= chars.length)
                return max;
        }
        return max;
    }

    public static void main(String[] args) {
        new MaxLengthSubString().lengthOfLongestSubstring("pwwkew");
    }
}
