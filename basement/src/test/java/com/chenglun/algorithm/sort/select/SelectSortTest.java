package com.chenglun.algorithm.sort.select;

import com.chenglun.algorithm.Util;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class SelectSortTest {

    @Test
    public void testSelectSort(){
        {
            int[] nums = {};
            selectSort(nums);
            assertArrayEquals(new int [] {}, nums);
        }
        {
            int[] nums = {1};
            selectSort(nums);
            assertArrayEquals(new int[] {1}, nums);
        }

        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {7, 6, 5, 4, 3, 2, 1};
            selectSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {1, 2, 3, 4, 5, 6, 7};
            selectSort(nums);
            assertArrayEquals(expect, nums);
        }
    }
    private void selectSort(int [] nums){
        if(nums == null || nums.length <= 1)  return ;
        for(int i = 0; i < nums.length - 1; ++i){
            int minIndex = i;
            int value = nums[i];
            for(int j = i+1; j < nums.length; ++j){
                if(value > nums[j]){
                    minIndex = j;
                    value = nums[j];
                }
            }
            Util.swap(nums, i, minIndex);
        }
    }
}
