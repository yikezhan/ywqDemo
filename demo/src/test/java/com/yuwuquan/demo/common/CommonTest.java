package com.yuwuquan.demo.common;

import java.util.*;
import java.util.stream.Collectors;

public class CommonTest {
    public static void main(String[] args) {
        int[][] a ={{1,2},{2,4},{4,6}};
        Arrays.sort(a, (v1,v2)->v1[1]<v2[1]?-1:1);

        List<Integer> b = new ArrayList<>();
        b.add(1);
        b.add(3);
        b.add(5);
        b.add(2);
        b.sort((v1,v2)-> v1<v2? -1 :1);
        System.out.println(b);
        print(a);
    }

    private static void print(int[][] a) {
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i].length;j++){
                System.out.print(a[i][j]+" ");
            }
            System.out.println();
        }
    }
}
