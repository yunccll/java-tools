package com.chenglun.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BackTrackingTest {
    @Test
    public void testPermutation(){
        int [] args = {3, 5, 7, 9, 11};
        boolean [] is = new boolean[args.length];
        int [] ans = new int[3];

        permute(0, ans.length, args, args.length, ans, is);
    }

    private void permute(int i, final int ansLength, int[] args,  final int argsLen, int[] ans, boolean [] is) {
        if(i == ansLength){
            Util.printArray(ans);
            return ;
        }
        for(int k = 0; k < argsLen; ++k){
            if(is[k] == false) {
                is[k] = true;
                ans[i] = args[k];
                permute(i + 1, ansLength, args, argsLen, ans, is);
                is[k] = false;
            }
        }
    }

    @Test
    public void testLetterCombinations(){
        letterCombinations("23").stream().forEach(System.out::println);
        letterCombinations("").stream().forEach(System.out::println);
    }
    private void backTracking(int i, int answerLength, String digits, List<String> result, char [] answer){
        if(i == answerLength){
            StringBuilder sb = new StringBuilder();
            for(char c : answer){
                sb.append(c);
            }
            result.add(sb.toString());
            return ;
        }

        //int iDigit =
        char [] objects = letterTabs[digits.charAt(i) - '0'];
        //Util.printCharArray(objects);
        for(int k = 0; k < objects.length; ++k){
            answer[i] = objects[k];
            backTracking(i+1, answerLength, digits, result, answer);
        }
    }

    public List<String> letterCombinations(String digits) {
        List<String> list = new ArrayList<>();
        if(digits == null || digits.isEmpty()){
            return list;
        }
        char [] answer = new char[digits.length()];
        backTracking(0, digits.length(), digits, list, answer);
        return list;
    }
    static char [][] letterTabs = {
            {'_',},  // 0
            {'!', '@', '#'}, //1
            {'a', 'b', 'c'}, //2
            {'d', 'e', 'f'}, //3
            {'g', 'h', 'i'}, //4
            {'j', 'k', 'l'}, //5
            {'m', 'n', 'o'}, //6
            {'p', 'q', 'r', 's'}, //7
            {'t', 'u', 'v'}, //8
            {'w', 'x', 'y', 'z'}, //9
    };
}
