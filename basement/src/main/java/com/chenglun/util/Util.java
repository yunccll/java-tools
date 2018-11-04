package com.chenglun.util;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Util {
    public static boolean equals(final Object left, final Object right)
    {
        return left == null ? right == null : left.equals(right);
    }

    public static class MapBuilder<K, V> {

        public static <T, E> MapBuilder<T, E> DEFAULT(){
            return new MapBuilder<T, E>();
        }


        private Map<K, V> map;
        public MapBuilder()
        {
            this.map = new HashMap<K, V>();
        }
        public MapBuilder<K, V> put(K key, V value)
        {
            this.map.put(key, value);
            return this;
        }
        public MapBuilder<K, V> clear(){
            this.map.clear();
            return this;
        }
        public Map<K,V> build()
        {
            Map<K, V> result  = map;
            this.map = new HashMap<K, V>();
            return result;
        }
    }
}
