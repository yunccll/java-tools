package com.chenglun;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class AppTest
{
    final static Logger logger = LoggerFactory.getLogger(AppTest.class);

    @BeforeClass
    public static void logInit()
    {
        //org.apache.log4j.BasicConfigurator.configure();
    }
    @Test
    public void testNumber() {
        {
            Integer i = 1245;
            Integer j = 1245;
            assertFalse(i == j);
            assertTrue(i.equals(j));
        }

        {
            int i = 1245;
            int j = 1245;
            assertTrue(i == j);
        }

        {
            //String  --> int ==> String ->integer -> int
            String istr = "12345";
            int i = Integer.valueOf(istr).intValue();
            int j = Integer.parseInt(istr); //better
        }
    }
    @Test
    public void testCharF() {
        // char --> int  auto-convert
        {
            int v = 'a' + 0;
            assertEquals(97, v);
        }

        //int -> char  force-convert
        {
            char ch = 'a';
            assertEquals((char)97, ch);
        }

        assertEquals(2, Character.BYTES);
    }

    @Test
    public void testStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("hello world\n").append("world");
        assertEquals("hello world\nworld", sb.toString());
    }

    @Test
    public void testDate() {
        long t = 1539224177317L;
        java.util.Date dt = new java.util.Date(t);
        assertEquals(t, dt.getTime());

    }
    @Test 
    public void testURI(){
        try{
            URI uri = new URI("http://www.baidu.com/help?key=value");        
            System.out.println(uri.toString());
        }
        catch(URISyntaxException e){
            e.printStackTrace();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testLogger() {
        logger.info("hello world");

    }
}
