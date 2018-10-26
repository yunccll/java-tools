package com.chenglun;

import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;

class RpcBuilder<V, T extends HttpRequestBase>{
    protected Client client;
    protected T httpMethod;

    public RpcBuilder(T httpMethod) {
        this.httpMethod = httpMethod;
    }

    public V setURI(final URI uri) {
        Args.assertNotNull(uri, "URI");
        this.httpMethod.setURI(uri);
        return (V)this;
    }

    public V setClient(final Client client) {
        Args.assertNotNull(client, "Client");
        this.client = client;

        Args.assertNotNull(this.client.getRequestConfig(), "request config is null");
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
