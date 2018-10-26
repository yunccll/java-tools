package com.chenglun;

import com.sun.org.apache.xpath.internal.Arg;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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

public class JsonHttpPostRpc implements Closeable{
    final static Logger logger = LoggerFactory.getLogger(JsonHttpPostRpc.class);

    public static class Builder extends  RpcBuilder<Builder, HttpPost>{
        public Builder(){
            super(new HttpPost());
        }
        public JsonHttpPostRpc build() {
            Args.assertNotNull(this.httpMethod, "HttpPost");
            return new JsonHttpPostRpc(this.client, this.httpMethod);
        }

        public static JsonHttpPostRpc.Builder createDefault(){
            return new JsonHttpPostRpc.Builder().setClient(Clients.createDefault());
        }
    }

    private Client client;
    private HttpPost httpPost;
    public JsonHttpPostRpc(final Client client, final HttpPost httpPost)
    {
        this.client = client;
        this.httpPost = httpPost;
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
    public Client getClient() {
        return this.client;
    }
    public URI getURI()
    {
        return this.httpPost.getURI();
    }
    public JsonHttpPostRpc setHeader(final String name, final String value){
        this.httpPost.setHeader(name, value);
        return this;
    }
    public JsonHttpPostRpc removeHeader(final String name){
        this.httpPost.removeHeaders(name);
        return this;
    }

    private String buildJsonBody(CloseableHttpResponse response) throws IOException
    {
        HttpEntity entity = response.getEntity();
        if(entity != null) {
            if (ContentType.APPLICATION_JSON.getMimeType().equals(ContentType.get(entity).getMimeType())) {
                return EntityUtils.toString(entity);
            } else {
                String errInfo = String.format("rsp contentType not the json, uri:[%s]\nresponse:[%s]", this.getURI().toString(),response.getStatusLine().toString());
                throw new ErrorContentTypeException(errInfo);
            }
        }
        logger.info("respone of url:[" + this.getURI().toString() + "] is no entity ");
        return null;
    }

    public String call(String json) throws IOException
    {
        Args.assertNotNull(this.httpPost, "HttpPost");
        StringEntity entity = new StringEntity(Args.isEmpty(json) ? "": json, ContentType.APPLICATION_JSON);
        this.httpPost.setEntity(entity);

        Args.assertNotNull(this.client, "Client");
        CloseableHttpResponse response = null;
        try {
            response = this.client.execute(this.httpPost);
            Args.assertNotNull(response, String.format("rsp is null, uri:[%s]", this.getURI().toString()));

            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200){
                return buildJsonBody(response);
            }
            else {
                String errInfo = String.format("status_code != 200, uri:[%s], \nresponse:[%s]", this.getURI().toString(), response.getStatusLine().toString());
                throw new StatusCodeException(statusCode, errInfo);
            }
        }
        //TODO: catch TimeoutException
        finally {
            if(response != null){
                response.close();
            }
        }
    }
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("TODO: request:uri,header,body:\n");
        sb.append(this.httpPost.toString() + "\n");
        for(Header he : this.httpPost.getAllHeaders()){
            sb.append(" [").append(he.toString()).append("] ");
        }
        return sb.toString();
    }

}
