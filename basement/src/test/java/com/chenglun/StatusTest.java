package com.chenglun;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.HashMap;

public class StatusTest {
    @Test
    public void testStatus(){

        Status<Integer> s = new Status<>(0, "OK", 1);
        System.out.println(s.toString());   

        Map<String, String> m = new HashMap<>();
        m.put("hello", "world");

        Status<Map<String, String>> s1 = new Status<>(0, "OK",m);
        System.out.println(s1.toString());   
    }
    public void testBuilder(){
        Status<Integer> s = Status<Integer>.Builder().build();
        System.out.println(s.toString());   
    }
}
