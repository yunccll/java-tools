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

import org.apache.http.client.config.RequestConfig;


public class HttpClientFluentTest
{
        
    @BeforeClass 
    public static void setup() {
        //org.apache.log4j.BasicConfigurator.configure();
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
        int cnt;
        public ThreadedHttpGet(int cnt){
            this.cnt = cnt;
        }
        @Override
        public void run(){
            System.out.printf("hello world, http count:%d\n", this.cnt);
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                try {
                    String url = "http://www.baidu.com";

                    HttpGet httpGet = new HttpGet(url);
                    RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
                        .setSocketTimeout(2000)
                        .setConnectionRequestTimeout(2000)
                        .build();
                    httpGet.setConfig(requestConfig);
                    
                    //httpGet.addHeader("Connection", "Keep-Alive");
                    long start = System.nanoTime();
                    long count = this.cnt;

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
        long start = System.nanoTime();
        long count = 10000;
        int threadCnt = 300;
        for(int i = 0 ; i < threadCnt; ++i)
        {
            Thread th = new ThreadedHttpGet( (int)(count/threadCnt));
            list.add(th);
            th.start();
        }

        System.out.println(list.size());

        for(Thread th : list){
            th.join();
        }

        long end = System.nanoTime();
        System.out.printf("total concurrent tps:%f\n", ((double)count)*1000*1000*1000/(end-start));
    }


    public void testHttpMultiGetWithSingleConnection() throws Exception
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String url = "http://www.baidu.com";
            HttpGet httpGet = new HttpGet(url);

            long start = System.nanoTime();
            long count = 100;

            for(int i = 0; i < count; i++){
                sendRequest(httpclient);
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
            String url = "http://www.baidu.com";
            HttpGet httpGet = new HttpGet(url);
            
            //httpGet.addHeader("Connection", "Keep-Alive");
            CloseableHttpResponse response1 = httpclient.execute(httpGet);

            try {
                System.out.println(response1.getStatusLine());

                HttpEntity entity1 = response1.getEntity();
                EntityUtils.consume(entity1);

            } 
            finally {
                response1.close();
            }

            System.out.println("...........................");
            Thread.currentThread().sleep(10000);
            System.out.println("...........................end....");
        } finally {
            httpclient.close();
        }
    }



    private void sendRequest(CloseableHttpClient client) throws Exception
    {
        String url = "http://www.baidu.com";
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response1 = client.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());

            HttpEntity entity1 = response1.getEntity();
            EntityUtils.consume(entity1);

        } finally {
            response1.close();
        }
    }
    private void sendRequestWithShortConnection() throws Exception
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            sendRequest(httpclient);

        } finally {
            httpclient.close();
        }
    }
    // long connection for each rout 
    // ConnectionPool  ==>    fetch connection from pool  key[route=url]
    public void testHttpGetWithConnectionPool() throws Exception
    {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(2);

        int count = 100;
        CloseableHttpClient [] hts = new CloseableHttpClient[count];
        for( int i = 0; i < count; ++i){
            hts[i] = HttpClients.custom().setConnectionManager(cm).build();
        }

        for(int i = 0; i < count; ++i){
            sendRequest(hts[i]);
        }

        System.out.println("...........................");
        Thread.currentThread().sleep(1000000);
        System.out.println("...........................end....");

        for( int i = 0; i < count; ++i){
            hts[i].close();
        }

    }

    //NOTE:   many connections with TIME_WAIT status
    @Test 
    public void testHttpGetWithNoPool() throws Exception
    {
        for( int i = 0 ; i < 1; ++i){
            sendRequestWithShortConnection();
        }
    }
}
