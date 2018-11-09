package com.chenglun.net.jsonrpc;

import com.chenglun.net.Client;
import com.chenglun.util.ArgsUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

public abstract class JsonHttpRpc implements Closeable
{
    final static Logger log = LoggerFactory.getLogger(JsonHttpRpc.class);

    protected Client client;
    protected HttpRequestBase httpRequest;

    public JsonHttpRpc(final Client client, final HttpRequestBase httpRequest) {
        this.client = client;
        this.httpRequest = httpRequest;
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
        return this.httpRequest.getURI();
    }

    public void setHeader(final String name, final String value){
        this.httpRequest.setHeader(name, value);
    }
    public void removeHeader(final String name){
        this.httpRequest.removeHeaders(name);
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
        log.info("respone of url:[" + this.getURI().toString() + "] is no entity ");
        return null;
    }


    public String call() throws IOException
    {
        return execute(this.client, this.httpRequest);
    }

    protected String execute(Client client, HttpRequestBase httpRequest) throws IOException {
        ArgsUtil.assertNotNull(client, "Client");
        ArgsUtil.assertNotNull(httpRequest, "HttpGet");

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpRequest);
            ArgsUtil.assertNotNull(response, String.format("rsp is null, uri:[%s]", this.getURI().toString()));

            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200){
                return buildJsonBody(response);
            }
            else {
                String errInfo = String.format("status_code != 200, uri:[%s], \nresponse:[%s]", this.getURI().toString(), response.getStatusLine().toString());
                throw new StatusCodeException(statusCode, errInfo);
            }
        }
        finally {
            if(response != null){
                response.close();
            }
        }
    }

    @Override
    public String toString()
    {
        //TODO: body
        StringBuilder sb = new StringBuilder("uri,header,body\n");

        sb.append(this.getURI().toString()).append('\n');

        for(Header he : this.httpRequest.getAllHeaders()){
            sb.append(" [").append(he.toString()).append("] ");
        }
        sb.append('\n');

        return sb.toString();
    }

}
