package com.chenglun;

import com.chenglun.net.jsonrpc.HttpArgs;
import com.chenglun.net.jsonrpc.JsonRpcFacade;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;


public class JsonRpcFacadeTest {
    static class ResultForAuth{
        @JsonProperty("ret")
        public int code;
        public String msg;
        @JsonProperty("auth_code")
        public String auth;
        @Override
        public String toString(){
            return String.format("code:%s,msg:%s,auth:%s", this.code, this.msg, this.auth);
        }
    };
    @Test
    public void use() throws IOException, URISyntaxException {

        {
            String url = "http://api.map.baidu.com/telematics/v3/weather";
            HttpArgs.Parameters parameters = new HttpArgs.Parameters()
                    .put("location", "嘉兴")
                    .put("output", "json")
                    .put("ak", "5slgyqGDENN7Sy7pw29IUvrZ");
            Result res = JsonRpcFacade.get(url, parameters);
            System.out.println(res.toString());

            HttpArgs.Data data = HttpArgs.Data.from(res.getDataAsMap());
            System.out.println(data.get("status").toString());
            System.out.println(data.get("message").toString());
        }

        {
            String url = "http://api.map.baidu.com/telematics/v3/weather?location=%25E5%2598%2589%25E5%2585%25B4&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
            Result res = JsonRpcFacade.get(url);
            System.out.println(res.toString());

            HttpArgs.Data data = HttpArgs.Data.from(res.getDataAsMap());
            System.out.println(data.get("status").toString());
            System.out.println(data.get("message").toString());
        }

        {
            String url = "http://live.admin.haotuoguan.cn/openapi/account/getauth";
            HttpArgs.Headers headers = new HttpArgs.Headers()
                    .put("Authorization", "a9cde0a2-6606-11e7-8f00-525400deb21e");

            HttpArgs.JsonData jsonMap = new HttpArgs.JsonData()
                    .put("account", "htg_test");

            ResultForAuth res = JsonRpcFacade.post(url, headers, jsonMap.getMap(), ResultForAuth.class);
            System.out.println(res.toString());
        }
        {
            System.out.println("----------------");
            String url = "http://live.admin.haotuoguan.cn/openapi/account/getauth";
            HttpArgs.Headers headers = new HttpArgs.Headers()
                    .put("Authorization", "a9cde0a2-6606-11e7-8f00-525400deb21e");

            HttpArgs.JsonData jsonMap = new HttpArgs.JsonData()
                    .put("account", "htg_test");

            Map<String, Object> res = JsonRpcFacade.post(url, null, headers, jsonMap.getMap(), Map.class);
            System.out.println(res.toString());
            System.out.println(res.get("ret").toString());
            System.out.println(res.get("msg").toString());
            System.out.println(res.get("auth_code").toString());
        }
    }
}
