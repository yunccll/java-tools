package com.chenglun;

import static org.junit.Assert.*;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.lang.Thread;

public class HttpClientFluentTest
{
        
    @BeforeClass 
    public static void setup(){
       //org.apache.log4j.BasicConfigurator.configure();
    }

    //TODO: Future use
    //TODO: see the fluent api source code && rewrite it
    public void testHttpJsonRpc() throws Exception
    {
        /*
        {
            JsonHttpRpc rpc = new JsonHttpRpc.createDefault();
            rpc.post<Map>(kwargs);
            rpc.close();
        }

        {
            HttpRpc rpc = new JsonHttpRpc(url, HttpConnection.createDefault());
            for( int i = 0; i < 100; ++i){
                Result ret = rpc.post<Map>(kwargs);
                System.out.println(ret.toString());
            }
            rpc.close();
        }

        {
            HttpConnection hc = new HttpConnection()
                .setKeepAlive(true)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout);

            HttpRpc rpc = new JsonHttpRpc(url, hc)
            for(int i = 0; i < 100; ++i)
            {
                Result ret = rpc.post<Map>(kwargs);
            }

            rpc.close();

            HttpRpc rpc2 = new JsonHttpRpc(url2, hc);
            rpc2.post<Map>(kwargs);
            rpc2.close();

            hc.close();
        }*/

        //result.code = ?
        //result.data["status"] = ?
        //result.data["json"] =  ?

        //req: 
        //  http://127.0.0.1:8080/call?k1=v1&&k2=v2&&k3=v3
        //  json = {
        //      "jk1" : "v1",
        //      "jk2" : "v2" 
        //  }

        //resp : status_code
        //     : 200 --> json = {
        //          "rsp_jk1" : "v1",
        //          "rsp_jk2" : "v2"
        //     }

    }




    public void testHttpPost() throws Exception
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "vip"));
            nvps.add(new BasicNameValuePair("password", "secret"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                EntityUtils.consume(entity2);
            } 
            finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
    }


    static class ThreadedHttpGet extends java.lang.Thread
    {
        @Override
        public void run(){
            System.out.println("hello world\n");
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                try {
                    String url = "http://127.0.0.1";
                    HttpGet httpGet = new HttpGet(url);
                    
                    httpGet.addHeader("Connection", "Keep-Alive");
                    long start = System.nanoTime();
                    long count = 10000;

                    for(int i = 0; i < count; i++){
                        CloseableHttpResponse response1 = httpclient.execute(httpGet);

                        try {
                            //System.out.println(response1.getStatusLine());

                            HttpEntity entity1 = response1.getEntity();
                            EntityUtils.consume(entity1);
                        } 
                        finally {
                            response1.close();
                        }
                    }
                    long end = System.nanoTime();
                    System.out.printf("tps:%f\n", ((double)count)*1000*1000*1000/(end-start));
                } finally {
                    httpclient.close();
                }
            }
            catch(final Exception e){
                System.out.println("exception out");
            }
        }
    }
    public void testThreadedHttpClient() throws Exception
    {
        System.out.println(".......................");
        List<Thread> list = new ArrayList<Thread>();
        int cnt = 10;
        for(int i = 0 ; i < cnt; ++i)
        {
            Thread th = new ThreadedHttpGet();
            list.add(th);
            th.start();
        }

        System.out.println(list.size());

        for(Thread th : list){
            th.join();
        }
    }

    public void testHttpMultiGet() throws Exception
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String url = "http://127.0.0.1";
            HttpGet httpGet = new HttpGet(url);
            
            httpGet.addHeader("Connection", "Keep-Alive");
            long start = System.nanoTime();
            long count = 10000;

            for(int i = 0; i < count; i++){
                CloseableHttpResponse response1 = httpclient.execute(httpGet);

                try {
                    //System.out.println(response1.getStatusLine());

                    HttpEntity entity1 = response1.getEntity();
                    EntityUtils.consume(entity1);
                } 
                finally {
                    response1.close();
                }
            }
            long end = System.nanoTime();
            System.out.printf("tps:%f\n", ((double)count)*1000*1000*1000/(end-start));
        } finally {
            httpclient.close();
        }
    }
    public void testHttpGet() throws Exception
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String url = "http://127.0.0.1";
            HttpGet httpGet = new HttpGet(url);
            
            httpGet.addHeader("Connection", "Keep-Alive");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);

            try {
                System.out.println(response1.getStatusLine());

                HttpEntity entity1 = response1.getEntity();
                EntityUtils.consume(entity1);

                System.out.println("...........................");
                Thread.currentThread().sleep(10000);
                System.out.println("...........................end....");
            } 
            finally {
                response1.close();
            }
        } finally {
            httpclient.close();
        }
    }
    @Test
    public void testHttpGetWithConnectionPool() throws Exception
    {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(100);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        try {
            String url = "http://www.baidu.com";
            HttpGet httpGet = new HttpGet(url);

            httpGet.addHeader("Connection", "Keep-Alive");


            for( int i = 0 ; i < 100; i++) {
                CloseableHttpResponse response1 = httpclient.execute(httpGet);
                try {
                    System.out.println(response1.getStatusLine());

                    HttpEntity entity1 = response1.getEntity();
                    EntityUtils.consume(entity1);

                } finally {
                    response1.close();
                }
                System.out.printf("%d...........................\n", i);
                Thread.currentThread().sleep(1000);
                System.out.printf("%d...........................end....\n",i);
            }

            System.out.println("...........................");
            Thread.currentThread().sleep(1000000);
            System.out.println("...........................end....");

        } finally {
            httpclient.close();
        }
    }
}
