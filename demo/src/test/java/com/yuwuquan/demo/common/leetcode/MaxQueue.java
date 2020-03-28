//按照上面的思路，我们可以设计这样的方法：从队列尾部插入元素时，我们可以提前取出队列中所有比这个元素小的元素，使得队列中只保留对结果有影响的数字。这样的方法等价于要求维持队列单调递减，即要保证每个元素的前面都没有比它小的元素。
//
//        那么如何高效实现一个始终递减的队列呢？我们只需要在插入每一个元素 value 时，从队列尾部依次取出比当前元素 value 小的元素，直到遇到一个比当前元素大的元素 value' 即可。
//
//        上面的过程保证了只要在元素 value 被插入之前队列递减，那么在 value 被插入之后队列依然递减。
//        而队列的初始状态（空队列）符合单调递减的定义。
//        由数学归纳法可知队列将会始终保持单调递减。
//        上面的过程需要从队列尾部取出元素，因此需要使用双端队列来实现。另外我们也需要一个辅助队列来记录所有被插入的值，以确定 pop_front 函数的返回值。
//
//        保证了队列单调递减后，求最大值时只需要直接取双端队列中的第一项即可。

package com.yuwuquan.demo.common.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

//看了评论区的思路后写的，
public class MaxQueue {
        private Deque<Integer> deque;
        private Deque<Integer> help;
        public MaxQueue() {
            deque = new ArrayDeque<Integer>();//队列
            help = new ArrayDeque<Integer>();//辅助双端队列，单调递减
        }

        public int max_value() {
            return (!deque.isEmpty())? help.peek() : -1;
        }

        public void push_back(int value) {
            deque.offer(value);
            while(!help.isEmpty() && help.peekLast()<value)
                help.pollLast();
            help.offer(value);
        }

        public int pop_front() {
            if(deque.isEmpty()) return -1;
            int val = deque.pop();
            if(val == help.peek()){
                help.pop();
            }
            return val;
        }
}
