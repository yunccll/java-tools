package com.chenglun.net.jsonrpc;

import com.chenglun.net.Client;
import com.chenglun.util.ArgsUtil;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;

class RpcBuilder<V, T extends HttpRequestBase>{
    protected Client client;
    protected T httpMethod;

    public RpcBuilder(T httpMethod) {
        this.httpMethod = httpMethod;
    }

    public V setURI(final URI uri) {
        ArgsUtil.assertNotNull(uri, "URI");
        this.httpMethod.setURI(uri);
        return (V)this;
    }

    public V setClient(final Client client) {
        ArgsUtil.assertNotNull(client, "Client");
        this.client = client;

        ArgsUtil.assertNotNull(this.client.getRequestConfig(), "request config is null");
        this.httpMethod.setConfig(this.client.getRequestConfig());
        return (V)this;
    }

    public V setHeader(final String key, final String value) {
        this.httpMethod.setHeader(key, value);
        return (V)this;
    }

    public V removeHeader(final String key) {
        this.httpMethod.removeHeaders(key);
        return (V)this;
    }

}
