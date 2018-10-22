package com.chenglun;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonHttpRpcTest {
    final static Logger logger = LoggerFactory.getLogger(JsonHttpRpcTest.class);

    private URI newURI()
    {
        String field = "%E5%98%89%E5%85%B4";
        try {
            System.out.println(URLDecoder.decode(field, "utf-8"));
            return new URIBuilder().setScheme("http")
                    .setHost("api.map.baidu.com")
                    .setPath("/telematics/v3/weather")
                    .setParameter("location", "%E5%98%89%E5%85%B4")
                    .setParameter("output", "json")
                    .setParameter("ak", "5slgyqGDENN7Sy7pw29IUvrZ")
                    .build();
        }
        catch(final UnsupportedEncodingException e){
            throw new URIBuildingException(String.format("decode field:[%s] error:", field) + e.toString());
        }
        catch (final URISyntaxException e){
            throw new URIBuildingException("uri syntax error:" + e.toString());
        }
    }

    @Test
    public void use() throws IOException
    {
        System.out.println("testInterface...................");

        JsonHttpGetRpc rpc = null;
        try {
            rpc = JsonHttpGetRpc.createDefault().setUri(this.newURI());

            String jsonResp = rpc.call();

            if (jsonResp != null)
                System.out.println(jsonResp.toString());
        }
        /*
        catch(final URIBuildingException e){
            throw new URIBuildingExceptionProxy(e);
        }
        catch(final StatusCodeException e){
            throw new StatusCodeExceptionProxy(e);
        }
        catch (final IOException e){
            throw new IOExceptionProxy(e);
        }*/
        finally {
            if(rpc != null)
                rpc.close();
        }
    }

    @Test
    public void withClient() throws IOException {
        {
            Client client = Client.createDefault();
            JsonHttpGetRpc rpc = JsonHttpGetRpc.create(client);
            rpc.setUri(newURI());
            rpc.call();
            rpc.close();
        }

        {
            //Client client = Client.createPool(Pool);
            Client client = Client.createDefault(); //==> client builder  timeout && pool
            JsonHttpGetRpc rpc = JsonHttpGetRpc.create(client); // ==> only from client
            rpc.setUri(newURI());
            rpc.call();
            rpc.close();
        }
    }
    //TODO: Future use
    //TODO: see the fluent api source code && rewrite it
    public void testHttpJsonRpc() throws Exception
    {
        /*
        {
            JsonPostHttpRpc rpc = new JsonHttpPostRpc.createDefault().setUri(uri);
            String json = rpc.call(String json);
            rpc.close();

            JsonGetHttpRpc rpc = new JsonHttpGetRpc.createDefault()setUri(uri);
            String json = rpc.call();
            rpc.close();
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
