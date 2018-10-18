package com.chenglun;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;

public class ResultTest {
    @Test
    public void testResult()
    {
        String expectOkStr = "code:[0] msg:[OK] data:[]";
        assertEquals(expectOkStr, Result.OK.build().toString());

        String expectFailedStr = "code:[-1] msg:[Common Failed] data:[]";
        assertEquals(expectFailedStr, Result.Failed.build().toString());

        String expectInternalError = "code:[-2] msg:[Internal Error] data:[]";
        assertEquals(expectInternalError, Result.InternalError.build().toString());
    }
    @Test
    public void testResultWithArgument()
    {
        Map<String, String> args = new HashMap<String, String>();
        args.put("hello", "World");
        args.put("hello1", "World2");

        String expectWithArgs = "code:[0] msg:[OK] data:[hello1:World2,hello:World,]";
        assertEquals(expectWithArgs, Result.OK.setData(args).build().toString());
    }

    @Test
    public void testWithBuilder()
    {

        Map<String, String> args = new HashMap<String, String>();
        args.put("hello", "World");
        args.put("hello1", "World2");

        Result ret = new Result.Builder(-102, "message--102", args).build();
        System.out.println(ret.toString());

        ret = new Result.Builder().setCode(-102).setMessage("message--102").setData(args).build();
        System.out.println(ret.toString());
    }

    @Test
    public void testWithExceptionBuilder()
    {
        Exception e = new Exception("hello world exception");
        Result ret = new Result.ExceptionBuilder(-100, e).build();
        String str =  ret.toString();

        ret = new Result.ExceptionBuilder(-100).setException(e).build();
        assertEquals(str, ret.toString());
    }
}
