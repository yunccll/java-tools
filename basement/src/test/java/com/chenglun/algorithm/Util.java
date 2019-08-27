package com.chenglun.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<Integer> toList(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        for (int i : nums) {
            ret.add(i);
        }
        return ret;
    }
}
