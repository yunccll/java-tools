package com.chenglun.algorithm.sort.insert;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class InsertSortTest {
    @Test
    public void testInsertSort(){
        {
            int[] nums = {};
            insertSort(nums);
            assertArrayEquals(new int [] {}, nums);
        }
        {
            int[] nums = {1};
            insertSort(nums);
            assertArrayEquals(new int[] {1}, nums);
        }

        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {7, 6, 5, 4, 3, 2, 1};
            insertSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {1, 2, 3, 4, 5, 6, 7};
            insertSort(nums);
            assertArrayEquals(expect, nums);
        }

        {

            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {3, 2, 1, 7, 5, 4, 6};
            insertSort(nums);
            assertArrayEquals(expect, nums);
        }
    }

    public void insertSort(int [] nums){
        for(int i = 1; i < nums.length; ++i){

            int val = nums[i];
            int j = i-1;
            for(; j >= 0 ; --j){
                if(val < nums[j]){
                    nums[j+1] = nums[j];
                }
                else
                    break;
            }
            nums[j+1] = val;
        }
    }
}
