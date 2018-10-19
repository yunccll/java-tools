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

import static org.junit.Assert.assertTrue;

public class JsonHttpRpcTest {

    static class Client implements  Closeable
    {
        public static Client createDefault(){
            return new Client();
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
        private String _url;
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

        public JsonHttpGetRpc setUrl(final String url){
            this._url = url;
            return this;
        }

        public JsonHttpGetRpc setClient(final Client client)
        {
            this._client = client;
            return this;
        }


        private Map<String, String> buildJsonBody(CloseableHttpResponse response) throws IOException {
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                if (ContentType.APPLICATION_JSON.getMimeType().equals(ContentType.get(entity).getMimeType())) {
                    String str = EntityUtils.toString(entity);
                    System.out.println(str);
                    //TODO: convert to json from text
                } else {
                    throw new ErrorResponseException("content-type is not the json");
                }
            }
            else{
                throw new ErrorResponseException("no response");
            }
            return new HashMap<>();
        }

        public Result call(Map<String,String> params, Map<String, String> body) throws IOException
        {
            Client client ;
            if(this._client == null){
                this.setClient(Client.createDefault());
            }
            client = this._client;

            if(this._httpGet == null){
                Args.assertNotEmpty(this._url, "url");
                this._httpGet = new HttpGet(this._url);
            }

            //TODO:
            //this.setParams(client, params);
            //this.<T>setBody(client, body);
            CloseableHttpResponse response = null;
            try {
                response = this._client.execute(this._httpGet);
                int status = response.getStatusLine().getStatusCode();
                System.out.println(status);
                Map<String, String> json = buildJsonBody(response);
                if(status == 200){
                    return (json == null) ? Result.OK() : Result.Builder.OK().setData(json).build();
                }
                else {
                    //TODO: create exception to build the status
                    if(json == null) {
                        json = new HashMap<>();
                        json.put("status", Integer.toString(status));
                    }
                    return Result.Builder.OK().setData(json).build();
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

    @Test
    public void testInterface() throws IOException {

        JsonHttpGetRpc rpc = JsonHttpGetRpc.createDefault();
        String url = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%98%89%E5%85%B4&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
        //String url = "http://www.baidu.com";
        rpc.setUrl(url);
        Map<String, String> param = new HashMap<>();
        Map<String, String> jsonArgs = new HashMap<>();
        rpc.<Map>call(param, jsonArgs);
        rpc.close();
    }
    //TODO: Future use
    //TODO: see the fluent api source code && rewrite it
    public void testHttpJsonRpc() throws Exception
    {
        /*
        {
            JsonHttpRpcTest rpc = new JsonHttpRpcTest.createDefault();
            rpc.setUrl(url);
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
