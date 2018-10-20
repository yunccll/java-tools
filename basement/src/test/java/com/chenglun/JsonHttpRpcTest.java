package com.chenglun;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.utils.URIBuilder;

import static org.junit.Assert.assertTrue;

public class JsonHttpRpcTest {

    static class Client implements  Closeable
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

    static class JsonHttpGetRpc implements Closeable
    {
        private Client _client ;
        private URI _uri;
        private HttpGet _httpGet;
        private JsonHttpGetRpc(Client client){
            _client = client;
        }

        public static JsonHttpGetRpc createDefault() {

            return new JsonHttpGetRpc(Client.createDefault());
        }

        @Override
        public void close() throws IOException {
            _client.close();
        }

        public JsonHttpGetRpc setUri(final URI uri) throws URISyntaxException
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


        private String buildJsonBody(CloseableHttpResponse response) throws IOException {
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                if (ContentType.APPLICATION_JSON.getMimeType().equals(ContentType.get(entity).getMimeType())) {
                    return EntityUtils.toString(entity);
                } else {
                    throw new ErrorResponseException("content-type is not the json");
                }
            }
            else{
                throw new ErrorResponseException("no response");
            }
        }

        public Result call(Map<String, String> body) throws IOException
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

            //TODO: this.<T>setBody(client, body);
            CloseableHttpResponse response = null;
            try {
                response = this._client.execute(this._httpGet);
                Args.assertNotNull(response, String.format("rsp from uri:[%s]", this._uri.toString()));
                int status = response.getStatusLine().getStatusCode();
                String jsonString = buildJsonBody(response);
                if(status == 200){
                    System.out.println(jsonString);
                    //TODO: json map 
                    return Args.isEmpty(jsonString)? Result.OK() : Result.Builder.OK().setData(new HashMap<String,String>()).build();
                }
                else {
                    //TODO: create exception to build the status
                    Map<String, String> map = new HashMap<>();
                    map.put("status", Integer.toString(status));
                    if(!Args.isEmpty(jsonString))
                        map.put("data", jsonString);
                    return Result.Builder.OK().setData(map).build();
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
    
    private URI newURI() throws URISyntaxException, UnsupportedEncodingException
    {
        String field = "%E5%98%89%E5%85%B4";
        try{
            System.out.println(URLDecoder.decode(field, "utf-8"));
        }
        catch(final UnsupportedEncodingException e){
            System.out.printf("decode field:[%s] failed\n", field);
            throw e;
        }

        return new URIBuilder().setScheme("http")
            .setHost("api.map.baidu.com")
            .setPath("/telematics/v3/weather")
            .setParameter("location", "%E5%98%89%E5%85%B4")
            .setParameter("output", "json")
            .setParameter("ak", "5slgyqGDENN7Sy7pw29IUvrZ")
            .build();
    }

    @Test
    public void testInterface() throws IOException, URISyntaxException, UnsupportedEncodingException
    {
        System.out.println("testInterface...................");

        JsonHttpGetRpc rpc = JsonHttpGetRpc.createDefault();
        rpc.setUri(this.newURI());

        Map<String, String> jsonArgs = new HashMap<>();
        Result res = rpc.<Map>call(jsonArgs);
        if(res != null)
            System.out.println(res.toString());
        rpc.close();
    }
    //TODO: Future use
    //TODO: see the fluent api source code && rewrite it
    public void testHttpJsonRpc() throws Exception
    {
        /*
        {
            JsonHttpRpcTest rpc = new JsonHttpRpcTest.createDefault();
            rpc.setUri(url);
            rpc.get<Map>(kwargs);
            rpc.post<Map>(kwargs);
            rpc.delete<Map>(kwargs);
            rpc.close();


            JsonPostHttpRpc rpc = new JsonHttpPostRpc.createDefault();
            JsonGetHttpRpc rpc = new JsonHttpGetRpc.createDefault();
            rpc.setUrl(url);
        }

        {
            HttpRpc rpc = new JsonHttpRpcTest(url, HttpConnection.createDefault());
            for( int i = 0; i < 100; ++i){
                Result ret = rpc.post<Map>(kwargs);
                System.out.println(ret.toString());
            }
            rpc.close();
        }

        {
            HttpConnection hc = new HttpConnection()
                .setKeepAlive(true)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout);

            HttpRpc rpc = new JsonHttpRpcTest(url, hc)
            for(int i = 0; i < 100; ++i)
            {
                Result ret = rpc.post<Map>(kwargs);
            }

            rpc.close();

            HttpRpc rpc2 = new JsonHttpRpcTest(url2, hc);
            rpc2.post<Map>(kwargs);
            rpc2.close();

            hc.close();
        }*/

        //result.code = ?
        //result.data["status"] = ?
        //result.data["json"] =  ?

        //req:
        //  http://127.0.0.1:8080/call?k1=v1&&k2=v2&&k3=v3
        //  json = {
        //      "jk1" : "v1",
        //      "jk2" : "v2"
        //  }

        //resp : status_code
        //     : 200 --> json = {
        //          "rsp_jk1" : "v1",
        //          "rsp_jk2" : "v2"
        //     }

    }
}
