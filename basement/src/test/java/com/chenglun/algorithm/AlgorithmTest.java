package com.chenglun.algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlgorithmTest {
    @Test
    public void test_lengthOfLongestSubstring() {
        lengthOfLongestSubstring(null);
        lengthOfLongestSubstring("");
        assertEquals(3, lengthOfLongestSubstring("abcabcbb"));
        assertEquals(1, lengthOfLongestSubstring("b"));
        assertEquals(1, lengthOfLongestSubstring("bbbbb"));
        assertEquals(3, lengthOfLongestSubstring("pwwkew"));
        assertEquals(26, lengthOfLongestSubstring("abcdefghijklmnopqrstuvwxyz"));
        assertEquals(3, lengthOfLongestSubstring("aabaab!bb"));
    }

    static class Hash {
        static final int len = 1 << 7;

        public Hash() {
            for (int i = 0; i < len; ++i) {
                map[i] = -1;
            }
        }

        public boolean containsKey(char ch) {
            return map[(byte) ch] != -1;
        }

        public void remove(char ch) {
            map[(byte) ch] = -1;
            --sz;
        }

        public void put(char ch, int index) {
            map[(byte) ch] = index;
            sz++;
        }

        public int get(char ch) {
            return map[(byte) ch];
        }

        public int size() {
            return this.sz;
        }

        int sz = 0;
        private int[] map = new int[len];
    }

    @Test
    public void testHash() {
        Hash h = new Hash();

        char ch = '1';
        h.put(ch, 0);
        int index = h.get(ch);
        assertEquals(true, h.containsKey(ch));
        assertEquals(1, h.size());
        h.remove(ch);
        assertEquals(0, h.size());
        assertEquals(false, h.containsKey(ch));
    }


    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) return 0;
        int max = 0;
        Hash map = new Hash();
        int start = 0;
        for (int tail = 0; tail < s.length(); ++tail) {
            char ch = s.charAt(tail);
            if (!map.containsKey(ch)) {
                map.put(ch, tail);
                continue;
            } else {
                if (max < map.size()) max = map.size();

                int end = map.get(ch);
                for (; start <= end; ++start) {
                    map.remove(s.charAt(start));
                }
                start = end + 1;
                map.put(ch, tail);
            }
        }
        if (max < map.size()) max = map.size();
        return max;
    }




    @Test
    public void testIntToRoman() {
        assertEquals("III", intToRoman2(3));
        assertEquals("IV", intToRoman2(4));
        assertEquals("IX", intToRoman2(9));
        assertEquals("LVIII", intToRoman2(58));
        assertEquals("MCMXCIV", intToRoman2(1994));
    }



    private static int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static String[] strs = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    public String intToRoman(int num) {  //Greedy Algorithm
        StringBuilder sb = new StringBuilder();
        for(int i =0; i < nums.length; ++i){
            while(num >= nums[i]){
                sb.append(strs[i]);
                num -= nums[i];
            }
        }
        return sb.toString();
    }

    private String [][] stab = {
            {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
            {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
            {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
            {"", "M", "MM", "MMM", "E" , "E", "E" , "E"  , "E"   , "E"},
    };
    public String intToRoman2(int num){
        String [] s = {"", "", "",""};
        int count = 0;
        do {
            int remainder = num%10;
            num = num/10;
            s[3-count] = stab[count][remainder];
            ++count;
        }while(num > 0);
        return String.join("",s);
    }


}
