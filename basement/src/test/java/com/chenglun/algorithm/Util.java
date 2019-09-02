package com.chenglun.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static <T> void printArray(T [] array){
        Arrays.stream(array).forEach(x -> {System.out.printf("%d, ", x);});
        System.out.println();
    }

    public static void printArray(int [] array){
        Arrays.stream(array).forEach(x -> {System.out.printf("%d, ", x);});
        System.out.println();
    }
    public static void printArray(long [] array){
        Arrays.stream(array).forEach(x -> {System.out.printf("%d, ", x);});
        System.out.println();
    }
    public static void printArray(char [] chars){
        for(char c : chars){
            System.out.print(c);
        }
        System.out.println();
    }

    public static <T> void swap(T [] array, int left, int right){
        T tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }
    public static void swap(int [] array, int left, int right){
        int tmp = array[left];
        array[left] = array[right];
        array[right] = tmp;
    }
}
