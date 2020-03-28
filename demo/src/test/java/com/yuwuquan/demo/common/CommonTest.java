package com.yuwuquan.demo.common;

import com.yuwuquan.demo.common.leetcode.ReVerseLink;

import java.math.BigDecimal;
import java.util.Optional;

public class CommonTest {
    public static void main(String[] args) {
        System.out.println(new Integer(1).equals(null));
    }
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public ListNode removeElements(ListNode head, int val) {
        while(head.val==val){
            head = head.next;
        }
        ListNode mHead = head;
        ListNode pre = head;
        while(head != null){
            if(head.val == val){
                pre.next = head.next;
            }
            pre = head;
            head = head.next;
        }
        return mHead;
    }
}
