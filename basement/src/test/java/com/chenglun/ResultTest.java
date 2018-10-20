package com.chenglun;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;

public class ResultTest {
    @Test
    public void testResult()
    {

        String expectOkStr = "code:[0] msg:[OK] data:[null]";
        assertEquals(expectOkStr, Result.OK().toString());

        String expectFailedStr = "code:[-1] msg:[Common Failed] data:[null]";
        assertEquals(expectFailedStr, Result.FAILED().toString());

        String expectInternalError = "code:[-2] msg:[Internal Error] data:[null]";
        assertEquals(expectInternalError, Result.INTERNAL_ERROR().toString());

        String expectNullResult = "code:[0] msg:[null] data:[null]" ;
        assertEquals(expectNullResult, new Result(0, null, null).toString());
    }

    @Test
    public void testResultWithArgument()
    {
        System.out.println("testResultWithArgument");
        Map<String, String> args = new HashMap<String, String>();
        args.put("hello", "World");
        args.put("hello1", "World2");

        String expectWithArgs = "code:[0] msg:[OK] data:[hello1:World2,hello:World,]";
        assertEquals(expectWithArgs, Result.Builder.OK().setData(args).build().toString());
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

        System.out.println("testWithExceptionBuilder");
        Exception e = new Exception("hello world exception");
        Result ret = new Result.Builder(-100, e).build();
        String expectStr = ret.toString();

        ret = new Result.Builder(-100).setException(e).build();
        assertEquals(expectStr, ret.toString());

        Result.Builder.DEFAULT().setCode(-100).setException(e).build();
        assertEquals(expectStr, ret.toString());

    }

    @Test
    public void testStaticBuilder()
    {
        Result ret = Result.OK();
        Result ret2 = Result.Builder.OK().setCode(0).setMessage("OK").build();
        assertTrue(ret.equals(ret2));
    }
}
