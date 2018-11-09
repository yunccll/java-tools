package com.chenglun.net.jsonrpc;

import com.chenglun.util.ArgsUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class JsonRpcFacade
{
    final static Logger log = LoggerFactory.getLogger(JsonRpcFacade.class);
    private static ObjectMapper om;
    static {
        om = new ObjectMapper();
    }
    public static <R> R get(String url, Class<R> clsReturn ) throws URISyntaxException, IOException {
        return get(url, null, clsReturn);
    }

    public static <R> R get(String url, HttpArgs.Parameters parameters, Class<R> clsReturn ) throws URISyntaxException, IOException {

        URIBuilder builder = new URIBuilder(url);
        if(parameters != null) {
            parameters.forEach((k, v) -> builder.addParameter(k, String.valueOf(v)));
        }

        JsonHttpRpc rpc = JsonHttpGetRpc.Builder.createDefault()
                .setURI(builder.build())
                .build();

        try {
            String jsonString = rpc.call();
            if( !ArgsUtil.isEmpty(jsonString)) {
                return om.readValue(jsonString, clsReturn);
            }
            else{
                log.warn("json-content-type, but no content");
                return null;
            }
        }
        finally {
            if(rpc != null)
                rpc.close();
        }
    }


    public static <R> R post(String url, Class<R> clsReturn) throws IOException, URISyntaxException {
        return post(url, null, null, null, clsReturn);
    }
    public static <R> R post(String url, HttpArgs.Parameters parameters, Class<R> clsReturn) throws IOException, URISyntaxException {
        return post(url, parameters, null, null, clsReturn);
    }
    public static <T, R> R post(String url, HttpArgs.Parameters parameters, T obj, Class<R> clsReturn) throws IOException, URISyntaxException {
        return post(url, parameters, null, obj, clsReturn);
    }
    public static  <T,R> R post(String url, T obj, Class<R> clsReturn) throws IOException, URISyntaxException {
        return post(url, null, null, obj, clsReturn);
    }
    public static <T, R> R post(String url, HttpArgs.Headers headers, T obj, Class<R> clsReturn) throws IOException, URISyntaxException {
        return post(url, null, headers, obj, clsReturn);
    }

    public static <T, R> R post(String url, HttpArgs.Parameters parameters, HttpArgs.Headers headers, T obj, Class<R> clsReturn) throws IOException, URISyntaxException
    {
        URIBuilder uriBuilder = new URIBuilder(url);
        if(parameters != null) {
            parameters.forEach((k, v) -> uriBuilder.addParameter(k, String.valueOf(v)));
        }

        JsonHttpPostRpc.Builder rpcBuilder  = JsonHttpPostRpc.Builder.createDefault()
                .setURI(uriBuilder.build());
        if(headers != null) {
            headers.forEach((k,v) -> rpcBuilder.setHeader(k, String.valueOf(v)));
        }

        if(obj != null){
            String json = om.writeValueAsString(obj);
            if(json != null)
                rpcBuilder.setJson(json);
        }

        JsonHttpRpc rpc = rpcBuilder.build();
        String ret = rpc.call();
        if( !ArgsUtil.isEmpty(ret)) {
            return om.readValue(ret, clsReturn);
        }
        else{
            //TODO: throw exception
            log.warn("json-content-type, but no content");
        }
        if(rpc != null)
            rpc.close();

        return null;
    }
}