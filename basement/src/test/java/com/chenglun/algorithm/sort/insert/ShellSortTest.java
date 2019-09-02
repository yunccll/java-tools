package com.chenglun.algorithm.sort.insert;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ShellSortTest {

    @Test
    public void testShellSort(){
        {
            int[] nums = {};
            shellSort(nums);
            assertArrayEquals(new int [] {}, nums);
        }
        {
            int[] nums = {1};
            shellSort(nums);
            assertArrayEquals(new int[] {1}, nums);
        }

        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {7, 6, 5, 4, 3, 2, 1};
            shellSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {1, 2, 3, 4, 5, 6, 7};
            shellSort(nums);
            assertArrayEquals(expect, nums);
        }

        {

            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {3, 2, 1, 7, 5, 4, 6};
            shellSort(nums);
            assertArrayEquals(expect, nums);
        }
    }

    public void shellSort(int [] nums){
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
}
