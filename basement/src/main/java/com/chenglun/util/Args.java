package com.chenglun.util;

public class Args {

    public static <T> T assertNotNull(final T argument, String name)
    {
        if(argument == null){
            throw new IllegalArgumentException(name + "is null");
        }
        return argument;
    }
    public static <T extends  CharSequence> T assertNotEmpty(final T argument, String name)
    {
        if(argument == null){
            throw new IllegalArgumentException(name + "is null");
        }
        else if (argument.length() == 0){
            throw new IllegalArgumentException(name + " length is 0");
        }
        return argument;
    }

    public static <T extends  CharSequence> boolean isEmpty(final T argument){
        if(argument == null){
            return true;
        }
        else if( argument.length() == 0){
            return true;
        }
        return false;
    }
}
