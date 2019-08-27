package com.chenglun.algorithm;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ThreeSumClosestTest {

    @Test(expected = IllegalArgumentException.class)
    public void testException() {
        int[] nums = {-1};
        threeSumClosest(nums, 1);
    }
    @Test
    public void test() {
        {
            int[] nums = {-1, 2, 1, -4};
            int ret = threeSumClosest(nums, 1);
            assertEquals(2, ret);
            //Util.toList(nums).stream().forEach(System.out::println);
        }
        {
            int[] nums = {1, 1, 1, 0};
            int ret = threeSumClosest(nums, 100);
            assertEquals(3, ret);
        }
        {
            int[] nums = { 1,1,-1,-1,3};
            int ret = threeSumClosest(nums, 3);
            assertEquals(3, ret);
        }
    }

    public int threeSumClosest(int [] nums, int target){
        if(nums == null  || nums.length < 3) {
            throw new IllegalArgumentException("input argument error!");
        }

        Arrays.sort(nums);
        //Util.toList(nums).stream().forEach(System.out::println);

        int minDv = Integer.MAX_VALUE;
        int minSum = Integer.MAX_VALUE;

        for(int i = 0; i < nums.length; ++i){
            int p = i+1;
            int k = nums.length - 1;
            while(p < k) {
                int sum = nums[i] + nums[p] +  nums[k];
                int dv = (target > sum) ? target - sum : sum - target;
                if(minDv > dv){
                    minSum = sum;
                    minDv = dv;
                }

                if(target > sum){
                    while(p < k && nums[p++] == nums[p]);
                }
                else if(target < sum){
                    while(p < k && nums[k--] == nums[k]);
                }
                else{
                    return target;
                }

                //System.out.printf("i:%d,p:%d,k:%d, tgt:%d,sum:%d,dv:%d\n", i, p, k, target, sum, dv);
            }
        }
        return minSum;
    }
}
