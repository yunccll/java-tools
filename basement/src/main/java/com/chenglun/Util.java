package com.chenglun;


public class Util {
    public static boolean equals(final Object left, final Object right)
    {
        return left == null ? right == null : left.equals(right);
    }
}
