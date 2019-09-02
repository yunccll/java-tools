package com.chenglun.algorithm;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SortTest {
    //For threeSum
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
