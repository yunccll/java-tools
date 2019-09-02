package com.chenglun.algorithm.sort.select;

import com.chenglun.algorithm.Util;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class HeapSortTest {

    @Test
    public void test(){
        {
            int[] nums = {};
            heapSort(nums);
            assertArrayEquals(new int [] {}, nums);
        }
        {
            int[] nums = {1};
            heapSort(nums);
            assertArrayEquals(new int[] {1}, nums);
        }

        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {7, 6, 5, 4, 3, 2, 1};
            heapSort(nums);
            assertArrayEquals(expect, nums);
        }
        {
            int[] expect = {1, 2, 3, 4, 5, 6, 7};
            int[] nums = {1, 2, 3, 4, 5, 6, 7};
            heapSort(nums);
            assertArrayEquals(expect, nums);
        }
    }

    private void heapSort(int [] arrays){
        if(arrays == null) return;

        makeHeap(arrays, arrays.length - 1);

        for(int i = arrays.length -1; i >=0; --i){
            Util.swap(arrays, 0, i);
            heapify(arrays, 0, i - 1);
        }
    }
    private void makeHeap(int [] arrays, int maxIndex){
        for(int i = (maxIndex-1)/2; i >= 0; --i){
            heapify(arrays, i, maxIndex);
        }
    }
    private void heapify(int [] arrays, int i, int maxIndex){
        if( i <= (maxIndex -1)/2){
            int left = 2 * i + 1;
            int largest = i;
            if(left <= maxIndex &&  arrays[left] > arrays[largest]){
                largest = left;
            }
            int right = 2 * i + 2;
            if(right <= maxIndex && arrays[right] > arrays[largest]){
                largest = right;
            }
            if(i != largest){
                Util.swap(arrays, i, largest);
                heapify(arrays, largest, maxIndex);
            }
        }
    }
}
