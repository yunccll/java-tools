package com.chenglun.algorithm;

import org.junit.Test;

import java.util.*;

public class ThreeSumTest {

    @Test
    public void testArrayToList() {
        Integer[] nums = {-1, 0, 1, 2, -1, -4};
        {
            Util.printArray(nums);
        }

        {
            List<Integer> list = new ArrayList<Integer>(Arrays.asList(nums));
            list.stream().forEach(System.out::println);
        }

        {
            List<Integer> list = new ArrayList<>(nums.length);
            Collections.addAll(list, nums);
            list.stream().forEach(System.out::println);
        }

        insertSort(nums);
    }

    public void insertSort(Integer[] nums) {
        for (int i = 1; i < nums.length; ++i) {
            int val = nums[i];
            int j = i - 1;
            for (; j >= 0; --j) {
                if (val < nums[j]) {
                    nums[j + 1] = nums[j];
                } else {
                    break;
                }
            }
            nums[j + 1] = val;
        }
    }




    public boolean unequal(List<Integer> ret, int first, int second, int third) {
        Integer[] newRet = ret.toArray(new Integer[ret.size()]);
        Arrays.sort(newRet);

        int[] r = new int[]{first, second, third};
        Arrays.sort(r);
        return !((newRet[0] == r[0]) && (newRet[1] == r[1]) && (newRet[2] == r[2]));
    }

    //Stupid    >> n^3
    public List<List<Integer>> threeSumStupid(int[] nums) {

        int len = nums.length;
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < len; ++i) {
            for (int j = i + 1; j < len; ++j) {
                for (int k = j + 1; k < len; ++k) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        //System.out.printf("%d:%d:%d\n", i, j ,k);
                        int ix = 0;
                        for (; ix < result.size() && unequal(result.get(ix), nums[i], nums[j], nums[k]); ++ix) ;
                        if (ix == result.size()) {
                            result.add(Arrays.asList((Integer) nums[i], (Integer) nums[j], (Integer) nums[k]));
                        }
                    }
                }
            }
        }
        return result;
    }

    public List<List<Integer>> threeSumDetail(int[] nums) {

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int len = nums.length;
        if (len < 3 || nums == null) return result;

        Arrays.sort(nums);

        for (int lastk = -1, k = 0; k < len; ++k) {
            if (lastk == -1 || nums[lastk] != nums[k]) {
                lastk = k;
                if (nums[k] < 0) {
                    int j = len - 1;
                    if (nums[j] <= 0) {  // k < 0 && [len-1] <= 0 => k + i + j < 0
                        break; // break k-loop
                    } else {  //k < 0,  j > 0; i = ?
                        boolean exit = false;
                        for (int i = k + 1, lasti = -1; i < j; ++i) {
                            if (lasti == -1 || nums[lasti] != nums[i]) {
                                lasti = i;
                                int sum = nums[k] + nums[i];
                                if (sum < 0) {
                                    for (int lastj = -1; j > i; --j) {
                                        if (lastj == -1 || nums[j] != nums[lastj]) {
                                            lastj = j;
                                            int sumAll = sum + nums[j];
                                            if (sumAll > 0) continue;
                                            else if (sumAll == 0) {
                                                result.add(Arrays.asList((Integer) nums[k], (Integer) nums[i], (Integer) nums[j]));
                                                break;
                                            } else {  //sum(k + i + j) < 0
                                                break;
                                            }
                                        }
                                    }
                                } else if (sum > 0) {
                                    exit = true;
                                    break;
                                } else if (sum == 0) {
                                    if (nums[i + 1] == 0)  // sum =0 , i +1 = 0 =>  k + i + j = 0 ; exit
                                        result.add(Arrays.asList((Integer) nums[k], (Integer) nums[i], (Integer) nums[i + 1]));
                                    //else nums[i+1] > 0  , k + i + j > 0
                                    break;
                                }
                            }
                        }
                        if (exit) break; // break k-loop
                    }
                } else if (nums[k] > 0)
                    break; //continue k-loop
                else { //[k] == 0;
                    int i = k + 1;
                    if (nums[i] == 0) { //  [k] = 0, [i] = 0
                        if (nums[i + 1] == 0) { // [k] = 0; [i] = 0; [i+1] = 0;
                            result.add(Arrays.asList((Integer) nums[k], (Integer) nums[i], (Integer) nums[i + 1]));
                        } else { // [k]=0; [i]>0 => [k] + [i] + [i+1] > 0;
                            break;
                        }
                    } else { // [k]=0, [i]>0 => [k]+[i] > 0
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Test
    public void test() {
        {
            int[] nums = {-1};
            threeSum(nums).stream().forEach(System.out::println);
        }
        {
            int[] nums = {0, 3, 0, 1, 1, -1, -5, -5, 3, -3, -3, 0};
            threeSum(nums).stream().forEach(System.out::println);
        }
        {
            int[] nums = {-1, 0, 1, 2, -1, -4};
            threeSum(nums).stream().forEach(System.out::println);
        }
        {
            int[] nums = {7, 5, -8, -6, -13, 7, 10, 1, 1, -4, -14, 0, -1, -10, 1, -13, -4, 6, -11, 8, -6, 0, 0, -5, 0, 11, -9, 8, 2, -6, 4, -14, 6, 4, -5, 0, -12, 12, -13, 5, -6, 10, -10, 0, 7, -2, -5, -12, 12, -9, 12, -9, 6, -11, 1, 14, 8, -1, 7, -13, 8, -11, -11, 0, 0, -1, -15, 3, -11, 9, -7, -10, 4, -2, 5, -4, 12, 7, -8, 9, 14, -11, 7, 5, -15, -15, -4, 0, 0, -11, 3, -15, -15, 7, 0, 0, 13, -7, -12, 9, 9, -3, 14, -1, 2, 5, 2, -9, -3, 1, 7, -12, -3, -1, 1, -2, 0, 12, 5, 7, 8, -7, 7, 8, 7, -15};
            threeSum(nums).stream().forEach(System.out::println);

        }
        {
            int[] nums = {12, 13, -10, -15, 4, 5, -8, 11, 10, 3, -11, 4, -10, 4, -7, 9, 1, 8, -5, -1, -9, -4, 3, -14, -11, 14, 0, -8, -6, -2, 14, -9, -4, 11, -8, -14, -7, -9, 4, 10, 9, 9, -1, 7, -10, 7, 1, 6, -8, 12, 12, -10, -7, 0, -9, -3, -1, -1, -4, 8, 12, -13, 6, -7, 13, 5, -14, 13, 12, 6, 8, -2, -8, -15, -10, -3, -1, 7, 10, 7, -4, 7, 4, -4, 14, 3, 0, -10, -13, 11, 5, 6, 13, -4, 6, 3, -13, 8, 1, 6, -9, -14, -11, -10, 8, -5, -6, -7, 9, -11, 7, 12, 3, -4, -7, -6, 14, 8, -1, 8, -4, -11};
            threeSum(nums).stream().forEach(System.out::println);
        }
    }
    public List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList<List<Integer>>();
        int len = nums.length;
        if (len < 3 || nums == null) return result;

        Arrays.sort(nums);
        //toList(nums).stream().forEach(System.out::println);

        for(int k = 0, lastk = -1; k < len; ++k){
            if(nums[k] > 0) break;
            if(lastk == -1 || nums[lastk] != nums[k]){ // [k] <= 0
                lastk = k;
                int i = k + 1;
                int j = len - 1;
                while(i < j){
                    int sum = nums[k] + nums[i] + nums[j];
                    if(sum == 0){
                        result.add(Arrays.asList(nums[k], nums[i], nums[j]));
                        while(i < j && nums[i] == nums[i+1]) ++i;
                        while(i < j && nums[j] == nums[j-1]) --j;
                        ++i;
                        --j;
                    }
                    else if(sum < 0) {
                        //while(i < j && nums[i] == nums[i+1]) ++i;  // most of test case :  -8, 2, 3, 4, 5, 6, 7, 8 , less case for -8,2,2,2,2,2,2,2,3,3,3,3,3,3
                        ++i;
                    }
                    else {
                        //while(i < j && nums[j] == nums[j-1]) --j; //most of test case :  -8, 2, 3, 4, 5, 6, 7, 8 , less case for -8,2,2,2,2,2,2,2,3,3,3,3,3,3
                        --j; //sum > 0
                    }
                }
            }
        }
        return result;
    }
}
