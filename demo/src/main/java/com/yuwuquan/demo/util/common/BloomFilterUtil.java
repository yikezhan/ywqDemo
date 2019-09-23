package com.yuwuquan.demo.util.common;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器:值多个映射到某个二进制位数上，全1即为存在，所以会有一定的误判率。在java中有现有的guava库提供的方法。使用如main方法。
 * redis的String是bitmap存储，且天生数据存放在内存中。适合这种算法，但是映射逻辑和误差率算法就需要自己写了。
 */
public class BloomFilterUtil {
    private static int size = 100000000;//总的数据量

    private static double fpp = 0.000001;//期望的误判率

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    /**
     * 以下为数据映射到内存的布隆过滤器
     * @param args
     *
     */
    public static void main(String[] args) {
        //插入1000000条数据
        for (int i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        int count = 0;
        for (int i = size; i < size*2; i++) {
            if (bloomFilter.mightContain(i)) {//包含在过滤器内
                count++;
                System.out.println(i + "误判了");
            }
        }
        System.out.println("误判数目:" + count);
    }
}
