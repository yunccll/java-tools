package com.chenglun;

import org.apache.http.client.config.RequestConfig;

public class ClientBuilder
{
    public ClientBuilder(){
    }

    private RequestConfig _requestCfg;
    private int _connectTimeout = 0;
    public ClientBuilder setConnectTimeout(int ms)
    {
        this._connectTimeout = ms;
        return this;
    }
    private int _readTimeout = 0;
    public ClientBuilder setReadTimeout(int ms)
    {
        this._readTimeout = ms;
        return this;
    }
    public Client build()
    {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.copy(RequestConfig.DEFAULT);
        if(this._connectTimeout > 0) {
            requestConfigBuilder.setConnectTimeout(this._connectTimeout);
        }
        if(this._readTimeout > 0) {
            requestConfigBuilder.setSocketTimeout(this._readTimeout);
        }

        return new Client(requestConfigBuilder.build());
    }
}
