package com.chenglun.algorithm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

//TODO: Search :  iterator, binarySearch
public class SearchTest {
    @Test
    public void testSearch(){
        {
            int[] nums = null;
            assertEquals(-1, search(nums, 2));
        }
        {
            int[] nums = {};
            assertEquals(-1, search(nums, 2));
        }
        {
            int[] nums = {3, 2, 1, 7, 5, 4, 6};
            assertEquals(1, search(nums, 2));
            assertEquals(-1, search(nums, -1));
        }
    }

    private int search(int [] nums, int target){
        if(nums == null) return  -1;
        for(int i = 0; i < nums.length; ++i){
            if(nums[i] == target) return i;
        }
        return -1;
    }


    @Test
    public  void testBinarySearch() {
        {
            int[] nums = {0, 1, 3, 3, 4, 5, 6, 7, 8};
            assertEquals(-1, binarySearch(nums, 2));
            assertEquals(6, binarySearch(nums, 6));
        }
        {
            int[] nums = {0, 1, 3, 3, 4, 5, 6, 7};
            assertEquals(-1, binarySearch(nums, 2));
            assertEquals(6, binarySearch(nums, 6));
        }
    }

    private int binarySearch(int [] nums, int target){
        if(nums == null) return -1;

        int start = 0;
        int end = nums.length -1;
        while(start <= end){
            int p = (start + end)/2;
            if (target > nums[p]){
                start = p + 1;
            }
            else if(target < nums[p]) {
                end = p - 1;
            }
            else{
              return p;
            }
        }
        return -1;
    }
}
