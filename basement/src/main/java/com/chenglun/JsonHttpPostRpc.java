package com.chenglun;

import com.sun.org.apache.xpath.internal.Arg;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class JsonHttpPostRpc extends  JsonHttpRpc
{
    // final static Logger logger = LoggerFactory.getLogger(JsonHttpPostRpc.class);

    public static class Builder extends  RpcBuilder<Builder, HttpPost>{
        public Builder(){
            super(new HttpPost());
        }

        private String json;
        public JsonHttpPostRpc build() {
            Args.assertNotNull(this.httpMethod, "HttpPost");
            return new JsonHttpPostRpc(this.client, this.httpMethod, this.json);
        }

        public JsonHttpPostRpc.Builder setJson(final String json){
            this.json = json;
            return this;
        }

        public static JsonHttpPostRpc.Builder createDefault(){
            return new JsonHttpPostRpc.Builder().setClient(Clients.createDefault());
        }
    }

    private String json;
    public JsonHttpPostRpc(final Client client, final HttpPost httpPost, final String json)
    {
        super(client, httpPost);
        this.json = json;
    }

    private void prepareRequestEntity(String json) {
        Args.assertNotNull(this.httpRequest, "HttpPost");
        StringEntity entity = new StringEntity(Args.isEmpty(json) ? "": json, ContentType.APPLICATION_JSON);
        ((HttpPost)this.httpRequest).setEntity(entity);
    }

    public String call() throws IOException
    {
        prepareRequestEntity(json);
        return super.call();
    }
}
