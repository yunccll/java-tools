package com.chenglun;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class JsonHttpRpcTest {


    public static class Result
    {
        //TODO:
        public static Result OK, FAILED;
        static {
            OK = new Result(0,"OK");
            FAILED = new Result(-1, "Common Failed");
        }
        private int _code ;
        private String _message;
        public Result(int code, String message){
            this._code = code;
            this._message = message;
        }

        private Map<String, String> _data;
        public Result setData(Map<String, String> data){
            this._data = data;
            return this;
        }
    }
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


        public Result call(Map<String,String> params, Map<String, String> body)
        {
            Client client ;
            if(this._client == null){
                this.setClient(Client.createDefault());
            }
            client = this._client;

            if(this._httpGet == null){
                //TODO: check url != null
                this._httpGet = new HttpGet(this._url);
            }

            //TODO:
            //this.setParams(client, params);
            //this.<T>setBody(client, body);
            try {
                CloseableHttpResponse response = this._client.execute(this._httpGet);
                int status = response.getStatusLine().getStatusCode();
                System.out.println(status);
                if(status == 200){
                    //TODO: check the context type
                    //TODO build json data
                    return Result.OK;
                }
                //TODO: create exception to build the status
                return Result.FAILED;
            }
            catch(final IOException e ){
                e.printStackTrace();
            }
            finally {
                //TODO:
            }
            //TODO: return Result???
            return Result.OK;
        }
    }

    @Test
    public void testInterface() throws IOException {

        JsonHttpGetRpc rpc = JsonHttpGetRpc.createDefault();
        rpc.setUrl("http://www.baidu.com");
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
