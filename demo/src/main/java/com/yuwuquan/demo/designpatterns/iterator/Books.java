package com.yuwuquan.demo.designpatterns.iterator;

import java.util.Iterator;

public class Books<K,V> implements Iterable<K>{
    private K[] keys;
    private V[] values;
    int size;

    public Books() {
        keys = (K[]) new Object[100];
        values = (V[]) new Object[100];
        size = 0;
    }
    public void put(K k,V v){
        keys[size] = k;
        values[size] = v;
        ++size;
    }

    //Iterable接口的方法，通常返回自己定义的KeyIterator即可。
    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    //内部迭代器类,实现怎么迭代
    public class KeyIterator implements Iterator<K> {
        private int ptr;

        public KeyIterator() {
            this.ptr = 0;
        }

        @Override
        public boolean hasNext() {
            return (ptr != size);
        }

        @Override
        public K next() {
            K returnItem = keys[ptr];
            ptr += 1;
            return returnItem;
        }
    }
}
