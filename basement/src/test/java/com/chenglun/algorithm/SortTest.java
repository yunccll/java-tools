package com.chenglun.algorithm;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SortTest {
    @Test
    public void tetShellSort(){
        Integer [] nums = {7, 6, 5, 4, 3, 2, 1};
        //shellSort(nums);
        insertSort(nums);
        insertSort(nums);
        Arrays.asList(nums).stream().forEach(System.out::println);
    }

    public void insertSort(Integer [] nums){
        for(int i = 1; i < nums.length; ++i){
            int val = nums[i];
            int j = i-1;
            for( ; j >= 0 ; --j){
                if(val < nums[j]){
                    nums[j+1] = nums[j];
                }
                else
                    break;
            }
            nums[j+1] = val;
        }
    }

    public void shellSort(Integer [] nums){
        if(nums == null || nums.length == 0) return ;

        for(int step = nums.length/2; step > 0; step /= 2){
            for(int i = step; i < nums.length; ++i){
                int val = nums[i];
                int j = i;
                for(; j >= step; j -= step){
                    if(val < nums[j - step]){
                        nums[j] = nums[j-step];
                    }
                    else{
                        break;
                    }
                }
                nums[j] = val;
            }
        }
    }

    @Test
    public void testMergeInsertSort(){
        Integer  [] nums = {0, 3, 0, 1, 1, -1, -5, -5, 3, -3, -3, 0};
        int len = mergeInsertSort(nums);
        assertEquals(6, len);
        Arrays.asList(nums).stream();
    }

    private int mergeInsertSort(Integer[] nums) {
        if(nums == null || nums.length == 0) return 0;
        Arrays.sort(nums);

        int p = 0;
        for(int i = 1; i < nums.length; ++i){
            if(nums[i] != nums[p]) {
                nums[++p] = nums[i];
            }
        }
        return p + 1;
    }
}
