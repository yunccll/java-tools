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
            rpc = JsonHttpGetRpc.Builder.createDefault().setURI(this.newURI()).build();

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
            JsonHttpGetRpc rpc = new JsonHttpGetRpc.Builder()
                .setClient(Clients.createDefault())
                .setURI(newURI())
                .build();
            rpc.call();
            rpc.close();
        }

        {
            JsonHttpGetRpc rpc = JsonHttpGetRpc.Builder.createDefault()
                    .setURI(newURI())
                    .build();
            rpc.call();
            rpc.close();
        }
    }

    @Test
    public void testHttpJsonRpc() throws Exception
    {
        URI uri = new URI("http://live.admin.haotuoguan.cn/openapi/account/getauth");
        String json = "{\"account\" : \"htg_test\"}";
        JsonHttpPostRpc post = JsonHttpPostRpc.Builder.createDefault()
                .setURI(uri)
                .setJson(json)
                .setHeader("Authorization","a9cde0a2-6606-11e7-8f00-525400deb21e" )
                .build();

        System.out.println(post.toString());
        String result = post.call();
        System.out.println(result);
        post.close();
    }
}
