package com.chenglun;

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

    private Client _client ;
    private URI _uri;
    private HttpGet _httpGet;
    private JsonHttpGetRpc(Client client){
        _client = client;
    }

    public static JsonHttpGetRpc createDefault() {

        return new JsonHttpGetRpc(Client.createDefault());
    }
    public static JsonHttpGetRpc create(Client client) {
        return new JsonHttpGetRpc(client);
    }

    @Override
    public void close() throws IOException {
        _client.close();
    }

    public JsonHttpGetRpc setUri(final URI uri)
    {
        Args.assertNotNull(uri, "uri");
        this._uri = uri;
        return this;
    }

    public JsonHttpGetRpc setClient(final Client client)
    {
        this._client = client;
        return this;
    }


    private String buildJsonBody(CloseableHttpResponse response) throws IOException
    {
        HttpEntity entity = response.getEntity();
        if(entity != null) {
            if (ContentType.APPLICATION_JSON.getMimeType().equals(ContentType.get(entity).getMimeType())) {
                return EntityUtils.toString(entity);
            } else {
                String errInfo = String.format("rsp contentType not the json, uri:[%s]\nresponse:[%s]", this._uri.toString(),response.toString());
                throw new ErrorContentTypeException(errInfo);
            }
        }
        logger.info("respone of url:[" + this._uri.toString() + "] is no entity ");
        return null;
    }

    public String call() throws IOException
    {
        Client client ;
        if(this._client == null){
            this.setClient(Client.createDefault());
        }
        client = this._client;

        if(this._httpGet == null){
            Args.assertNotNull(this._uri, "uri");
            this._httpGet = new HttpGet(this._uri);
        }

        CloseableHttpResponse response = null;
        try {
            response = this._client.execute(this._httpGet);
            Args.assertNotNull(response, String.format("rsp is null, uri:[%s]", this._uri.toString()));

            logger.info(response.toString());
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200){
                String jsonString = buildJsonBody(response);
                //System.out.println(jsonString);
                return jsonString;
            }
            else {
                String errInfo = String.format("status_code != 200, uri:[%s], \nresponse:[%s]", this._uri.toString(), response.toString());
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
}
