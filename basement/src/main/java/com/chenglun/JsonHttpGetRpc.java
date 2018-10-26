package com.chenglun;

import com.sun.org.apache.xpath.internal.Arg;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

public class JsonHttpGetRpc implements Closeable {
    final static Logger logger = LoggerFactory.getLogger(JsonHttpGetRpc.class);

    public static class Builder extends  RpcBuilder<Builder, HttpGet>
    {
        public Builder() {
            super(new HttpGet());
        }
        public JsonHttpGetRpc build() {
            Args.assertNotNull(this.httpMethod, "HttpGet");
            return new JsonHttpGetRpc(this.client, this.httpMethod);
        }

        public static Builder createDefault(){
            return new JsonHttpGetRpc.Builder().setClient(Clients.createDefault());
        }
    }

    private Client client;
    private HttpGet httpGet;

    private JsonHttpGetRpc(final Client client, final HttpGet httpGet) {
        this.client = client;
        this.httpGet = httpGet;
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
        return this.httpGet.getURI();
    }

    public JsonHttpGetRpc setHeader(final String name, final String value){
        this.httpGet.setHeader(name, value);
        return this;
    }
    public JsonHttpGetRpc removeHeader(final String name){
        this.httpGet.removeHeaders(name);
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

    public String call() throws IOException
    {
        Args.assertNotNull(this.client, "Client");
        Args.assertNotNull(this.httpGet, "HttpGet");

        CloseableHttpResponse response = null;
        try {
            response = this.client.execute(this.httpGet);
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
        sb.append(this.httpGet.toString() + "\n");
        for(Header he : this.httpGet.getAllHeaders()){
            sb.append(" [").append(he.toString()).append("] ");
        }
        return sb.toString();
    }
}
