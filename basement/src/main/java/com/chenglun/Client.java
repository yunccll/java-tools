package com.chenglun;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;
import java.io.IOException;

//TODO:
public class Client implements Closeable
{
    public static Client DEFAULT;
    static {
        DEFAULT = new Client();
    }

    public static Client createDefault(){
        return Client.DEFAULT;
    }

    private CloseableHttpClient _httpclient;
    public Client(){
        _httpclient = HttpClients.createDefault();
    }

    @Override
    public void close() throws IOException {
        this._httpclient.close();
    }

    public CloseableHttpResponse execute(HttpGet httpGet) throws IOException {
        return this._httpclient.execute(httpGet);
    }
}
