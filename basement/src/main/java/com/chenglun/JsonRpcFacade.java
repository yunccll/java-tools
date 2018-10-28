package com.chenglun;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class JsonRpcFacade
{
    final static Logger logger = LoggerFactory.getLogger(JsonRpcFacade.class);
    private static ObjectMapper om;
    static {
        om = new ObjectMapper();
    }

    public static Result get(String url) throws IOException, URISyntaxException
    {
        return get(url, null);
    }
    public static Result get(String url, HttpArgs.Parameters parameters) throws URISyntaxException, IOException {

        URIBuilder builder = new URIBuilder(url);
        if(parameters != null) {
            parameters.forEach((k, v) -> builder.addParameter(k, String.valueOf(v)));
        }

        JsonHttpRpc rpc = JsonHttpGetRpc.Builder.createDefault()
                .setURI(builder.build())
                .build();

        String ret = null;
        try {
            ret = rpc.call();
            if( !Args.isEmpty(ret)) {
                Map<String, Object> json = om.readValue(ret, Map.class);
                return Result.OK().setData(json);
            }
            else{
                logger.warn("json-content-type, but no content");
            }
        }
        catch (final IOException e){
            return Result.FAILED().setException(e);
        }
        finally {
            if(rpc != null)
                rpc.close();
        }
        return Result.OK();
    }




    public static <R> R post(String url, Class<R> cls) throws IOException, URISyntaxException {
        return post(url, null, null, null, cls);
    }
    public static <R> R post(String url, HttpArgs.Parameters parameters, Class<R> cls) throws IOException, URISyntaxException {
        return post(url, parameters, null, null, cls);
    }
    public static <T, R> R post(String url, HttpArgs.Parameters parameters, T obj, Class<R> cls) throws IOException, URISyntaxException {
        return post(url, parameters, null, obj, cls);
    }
    public static  <T,R> R post(String url, T obj, Class<R> cls) throws IOException, URISyntaxException {
        return post(url, null, null, obj, cls);
    }
    public static <T, R> R post(String url, HttpArgs.Headers headers, T obj, Class<R> cls) throws IOException, URISyntaxException {
        return post(url, null, headers, obj, cls);
    }

    public static <T, R> R post(String url, HttpArgs.Parameters parameters, HttpArgs.Headers headers, T obj, Class<R> cls) throws IOException, URISyntaxException
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
        if( !Args.isEmpty(ret)) {
            return om.readValue(ret, cls);
        }
        else{
            //TODO: throw exception
            logger.warn("json-content-type, but no content");
        }
        if(rpc != null)
            rpc.close();

        return null;
    }
    /*
    public static <T> Result post(String url, HttpArgs.Parameters parameters, HttpArgs.Headers headers, T obj) throws IOException, URISyntaxException {

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
        String ret = null;
        try {
            ret = rpc.call();
            if( !Args.isEmpty(ret)) {
                Map<String, Object> json = om.readValue(ret, Map.class);
                return Result.OK().setData(json);
            }
            else{
                logger.warn("json-content-type, but no content");
            }
        }
        catch (final IOException e){
            return Result.FAILED().setException(e);
        }
        finally {
            if(rpc != null)
                rpc.close();
        }
        return Result.OK();
    }
    */

}