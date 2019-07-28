package com.chenglun;



import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;


//Note:
//  Map (single);
//  Collection <-- Set, List, Queue
//  Collection contain Iterator

public class CollectionTest
{
    public <T> void printArray(T [] arr){
        for(final T e : arr){
            System.out.printf("%s,", e);
        }
        System.out.println();
    }
    @Test
    public void testArray()
    {
        final Integer [] expect = {1,3,34,53,334, 4234};
        Integer [] iarr = {1, 3, 4234, 34, 53, 334};
        Arrays.sort(iarr);
        assertTrue(Arrays.equals(expect, iarr));
    }
    @Test
    public void testList()
    {
        List<Integer> l = new ArrayList<Integer>();
        l.add(1);
        l.add(2);
        printArray(l.toArray());
    }
    @Test
    public void testMap()
    {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("chenglun", 1);
        m.put("chenglun", 1);
        for(Map.Entry<String, Integer> e : m.entrySet())
        {
            System.out.printf("<%s,%s>", e.getKey(), e.getValue());
        }
        System.out.println();
    }
    @Test
    public void testIterator()
    {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("chenglun", 1);
        m.put("chenglun2", 2);
        Iterator<String> it = m.keySet().iterator();
        while(it.hasNext()){
            String str = it.next();
            System.out.printf("%s,%s\n", str, m.get(str));
        }
    }
    @Test
    public void testStreaming(){
        final Integer [] data = {1, 3, 4, 5, 6, 83}  ;

        Arrays.stream(data).forEach(x->{ System.out.println(x);});

        Consumer<Integer> action = System.out::println;
        Arrays.stream(data).forEach(x-> {action.accept(x);});

        Arrays.stream(data).forEach(System.out::println);

    }
}

