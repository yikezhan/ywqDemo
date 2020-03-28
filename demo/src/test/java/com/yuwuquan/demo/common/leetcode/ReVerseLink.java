package com.yuwuquan.demo.common.leetcode;

public class ReVerseLink {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    static ListNode listNodeEnd;
    public ListNode reverseList(ListNode head) {//非递归反转
        ListNode curren = head;
        ListNode pre = null;
        while(curren!= null){
            ListNode next = curren.next;
            curren.next = pre;
            pre = curren;
            curren = next;
        }
        return pre;
    }
    public ListNode reverseList1(ListNode head) {//递归反转
        myReverse(head);
        return listNodeEnd;
    }

    public ListNode myReverse(ListNode head) {
        if(head.next == null) {
            listNodeEnd = head;
            return head;
        }
        ListNode listNode = new ListNode(head.val);
        myReverse(head.next).next = listNode;
        return listNode;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        l1.next = l2;
        ListNode l3 = new ListNode(3);
        l2.next = l3;
        new ReVerseLink().reverseList(l1);
        while(listNodeEnd != null){
            System.out.println(listNodeEnd.val);
            listNodeEnd = listNodeEnd.next;
        }
    }
}
