package com.chenglun.algorithm.sort.bubble;

import com.chenglun.algorithm.Util;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class BubbleSortTest {

    @Test
    public void testBubbleSort(){
        {
            int[] nums = {};
            bubbleSort(nums);
            assertArrayEquals(new int [] {}, nums);
        }
        {
            int[] nums = {1};
            bubbleSort(nums);
            assertArrayEquals(new int[] {1}, nums);
        }

        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {7, 6, 5, 4, 3, 2, 1};
            bubbleSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {1, 2, 3, 4, 5, 6, 7};
            bubbleSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {3, 2, 1, 7, 5, 4, 6};
            bubbleSort(nums);
            assertArrayEquals(expect, nums);
        }
    }
    private void bubbleSort(int [] nums){
        for(int i = 0; i < nums.length - 1; ++i){

            for(int j = 0; j < nums.length - i - 1; ++j){
                if(nums[j] > nums[j+1]){
                    Util.swap(nums, j , j +1);
                }
            }

        }
    }
}
