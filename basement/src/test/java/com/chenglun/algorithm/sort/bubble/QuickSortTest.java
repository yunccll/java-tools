package com.chenglun.algorithm.sort.bubble;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class QuickSortTest {

    @Test
    public void testQuickSort(){
        {
            int[] nums = {};
            quickSort(nums);
            assertArrayEquals(new int [] {}, nums);
        }
        {
            int[] nums = {1};
            quickSort(nums);
            assertArrayEquals(new int[] {1}, nums);
        }

        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {7, 6, 5, 4, 3, 2, 1};
            quickSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {1, 2, 3, 4, 5, 6, 7};
            quickSort(nums);
            assertArrayEquals(expect, nums);
        }
        {

            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {3, 2, 1, 7, 5, 4, 6};
            quickSort(nums);
            assertArrayEquals(expect, nums);
        }
    }
    private void quickSort(int [] nums){
        sortRange(nums, 0, nums.length-1);
    }

    private void sortRange(int [] nums, int low, int high){
        if(low < high) {
            int pivot = oneShot(nums, low, high);
            sortRange(nums, 0, pivot - 1);
            sortRange(nums, pivot + 1, high);
        }
    }

    private int oneShot(int [] nums, int low, int high){
        int tmp = nums[low];
        while (low < high) {
            while (low < high && tmp < nums[high]) --high;
            nums[low] = nums[high];
            while (low < high && tmp > nums[low]) ++low;
            nums[high] = nums[low];
        }
        nums[low] = tmp;
        return low;
    }

}
