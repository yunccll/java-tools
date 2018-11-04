package com.chenglun;

import com.chenglun.net.Client;
import com.chenglun.net.Clients;
import com.chenglun.net.jsonrpc.JsonHttpGetRpc;
import com.chenglun.net.jsonrpc.URIBuildingException;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

public class ClientTest {

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
        {
            Client client = Clients.createDefault();
            client.close();
        }
        {
            Client client = Clients.custom()
                    .setConnectTimeout(1000)
                    .setReadTimeout(1000).build();

            JsonHttpGetRpc rpc = new JsonHttpGetRpc.Builder()
                    .setClient(client)
                    .setURI(newURI())
                    .setHeader("Authorize", "xxxxxxxxxxxxxxxxxx")
                    .build();

            System.out.println(rpc.toString());
            String result = rpc.call();
            System.out.println(result);

            for( int i = 0; i < 3; ++i) {
                System.out.println(rpc.toString());
                rpc.call();
                System.out.println(result);
            }

            for( int i = 0; i < 3; ++i) {
                rpc.setHeader("Authorize", "xx" + String.valueOf(i));
                System.out.println(rpc.toString());
                result = rpc.call();
                System.out.println(result);
            }

            client.close();
        }
    }
}
