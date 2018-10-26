package com.chenglun;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;
import java.io.IOException;

//TODO:
public class Client implements Closeable
{

    private RequestConfig requestConfig;
    public RequestConfig getRequestConfig() {
        return this.requestConfig;
    }

    private CloseableHttpClient httpclient;
    public Client(RequestConfig requestConfig) {
        this.httpclient = HttpClients.createDefault();

        if(requestConfig == null){
            requestConfig = RequestConfig.copy(RequestConfig.DEFAULT).build();
        }
        this.requestConfig = requestConfig;
    }

    @Override
    public void close() throws IOException
    {
        this.httpclient.close();
    }

    public CloseableHttpResponse execute(HttpRequestBase request) throws IOException
    {
        return this.httpclient.execute(request);
    }
}
