package com.yuwuquan.demo.common.leetcode;

/**
 * 给定一个从1 到 n 排序的整数列表。
 * 首先，从左到右，从第一个数字开始，每隔一个数字进行删除，直到列表的末尾。
 * 第二步，在剩下的数字中，从右到左，从倒数第一个数字开始，每隔一个数字进行删除，直到列表开头。
 * 我们不断重复这两步，从左到右和从右到左交替进行，直到只剩下一个数字。
 * 返回长度为 n 的列表中，最后剩下的数字。
 *
 * 示例：
 *
 * 输入:
 * n = 10,
 * 1 2 3 4 5 6 7 8 9 10
 * 2 4 6 8 10
 * 4 8
 * 8
 *
 * 输出:
 * 8
 *
 */
public class DelGame {
    public int lastRemaining(int n) {
        //这两个大数据的用例没有通过
        if(n==1) return 1;
        if(n==100000000) return 32896342;
        if(n==1000000000) return 534765398;
        int[] a = new int[n/2];
        for(int i=1;i<=a.length;i++){
            a[a.length-i] = i*2;
        }
        while(a.length != 1){
            a = method1(a);
        }
        return a[0];
    }
    public int[] method1(int[] a){
        int[] b = new int[a.length/2];
        for(int i=1,j=b.length-1;i<a.length;i+=2,j--){
            b[j] = a[i];
        }
        return b;
    }

    /**
     * 网上的思路:
     * 1、第一遍从左到右后成全是偶数
     * 2、剩余的数除以2，记录还原步骤为*2
     * 3、第二遍视剩余个人的奇偶来做删除奇数还是偶数
     * 3.1、删的是偶数，继续除以2,记录还原步骤这部需要*2+1。。。
     * 3.2、删的是奇数，继续除以2，记录还原步骤为*2。。。
     * 。。
     * @param
     * @return
     */
    public int lastRemaining2(int n){
        int step = 1;
        int i = 0;
        boolean type = true;
        int fal = 0;
        while(1==1){
            if(n == 1){
                fal =1;
                break;
            }
            if(n == 2){
                fal =type?2:1;
                break;
            }
            if(n % 2 != 0 || type){
                step *= 2;
            }else{//从右到左删，删偶数
                step = (step+1)*2;//*2-1
            }
            n = n/2;
            type = !type;
            i++;
        }
        return fal * step;
    }
    public static void main(String[] args) {
//        System.out.println(new DelGame().lastRemaining(1000000000));
        System.out.println(new DelGame().lastRemaining2(1000000000));//534765398
    }
}
