package com.chenglun.algorithm.sort;

import com.chenglun.algorithm.Util;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class MergeSortTest {
    @Test
    public void testMergeSort(){
        {
            int[] nums = {};
            mergeSort(nums);
            assertArrayEquals(new int [] {}, nums);
        }
        {
            int[] nums = {1};
            mergeSort(nums);
            assertArrayEquals(new int[] {1}, nums);
        }

        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {7, 6, 5, 4, 3, 2, 1};
            mergeSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {1, 2, 3, 4, 5, 6, 7};
            mergeSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {3, 2, 1, 7, 5, 4, 6};
            mergeSort(nums);
            assertArrayEquals(expect, nums);
        }
    }
    private void mergeSort(int [] nums){
        _mergeSort(nums, 0, nums.length -1);
    }
    //=>[start, end]
    private void _mergeSort(int [] nums, int start, int end){
        if(start < end) {
            int p = (start + end) / 2;
            _mergeSort(nums, start, p);
            _mergeSort(nums, p + 1, end);
            merge(nums, start, p, end);
        }
    }
    private void merge(int [] nums, int start, int p, final int end){
        int [] tmp = new int[end - start + 1];
        final int mid = p;
        final int offset = start;

        int i = 0;
        ++p;
        while( start <= mid && p <= end){
            if(nums[start] <= nums[p]){
                tmp[i++] = nums[start++];
            }
            else{
                tmp[i++]  = nums[p++];
            }
        }
        while(start <= mid){
            tmp[i++] = nums[start++];
        }
        while(p <= end){
            tmp[i++] = nums[p++];
        }
        for(i = 0; i < tmp.length; ++i){
            nums[offset + i] = tmp[i];
        }
    }
}
