package com.chenglun;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JacksonTest {

    public void testJackson() throws IOException {
        ObjectMapper om = new ObjectMapper();
        {
            String json = "{\"account\" : \"htg_test\"}";
            Map<String, Object> m = om.readValue(json, Map.class);
            System.out.println(m);
        }
    }

    static class ResultFacade<T> {
        public ResultFacade(){
        }
        @JsonProperty("code")
        private int code;
        public int getCode(){
            return this.code;
        }
        public void setCode(final int code){
            this.code = code;
        }
        @JsonProperty("message")
        private String message;
        public String getMessage(){
            return this.message;
        }
        public void setMessage(final String message){
            this.message = message;
        }
        @JsonProperty("data")
        private T data;
        public T getData(){
            return data;
        }
        public void setData(final T data){
            this.data = data;
        }
        @Override
        public String toString(){
            return String.format("code:%d, message:%s, data:%s",this.code, this.message, this.data.toString());
        }
    }

    static class Data {
        public Data(){
        }
        public Data(final String token, final String refreshToken){
            this.token = token;
            this.refreshToken = refreshToken;
        }
        @JsonProperty("token")
        private String token;
        public String getToken(){
            return token;
        }
        public void setToken(final String token){
            this.token = token;
        }
        @JsonProperty("refresh_token")
        private String refreshToken;
        public String getRefreshToken(){
            return this.refreshToken;
        }
        public void setRefreshToken(final String refreshToken){
            this.refreshToken = refreshToken;
        }
        @Override
        public String toString(){
            return String.format("token:%s, refreshToken:%s",this.token, this.refreshToken);
        }
    }
    static class MapData {
        public MapData(){
            mapData = new HashMap<String, Data>();
            listData = new LinkedList<Data>();
        }
        @JsonProperty("map_data")
        public Map<String, Data> mapData;
        @JsonProperty("list_data")
        public List<Data> listData;

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder("map:\t[");
            for(Map.Entry<String, Data> e : this.mapData.entrySet()){
                sb.append(String.format("key:%s, value:%s,", e.getKey(), e.getValue()));
            }
            sb.append("]\nlist:\t[");
            for(Data d : this.listData){
                sb.append(String.format("%s,",d));
            }
            sb.append("]\n");
            return sb.toString();
        }
    }
    static class ComplicatedData{
        public ComplicatedData(){
        }
        @JsonProperty("data")
        private Data data;
        public Data getData(){
            return this.data;
        }
        public void setData(final Data data){
            this.data = data;
        }
        @JsonProperty("code")
        private int code;
        public int getCode(){
            return this.code;
        }
        public void setCode(final int code){
            this.code = code;
        }
        @Override
        public String toString(){
            return "complicatedData:\n\t[" + String.format("code:[%d], data:[%s]]", this.code, this.data);
        }
    }
    private static ObjectMapper OM ;
    static {
        OM = new ObjectMapper();
    }

    @Test
    public void toJson() throws JsonProcessingException {
        Data d = new Data("token1", "refresh_token1");
        System.out.println(OM.writeValueAsString(d));
    }
    @Test
    public void fromJsonToPlainObject() throws IOException {
        String jstr = "{\"token\":\"token1\",\"refresh_token\":\"refresh_token1\"}";
        Data d = OM.readValue(jstr, Data.class);
        System.out.println(d.toString());
    }

    @Test
    public void toJsonMap() throws JsonProcessingException {
        MapData mapD = new MapData();
        mapD.mapData.put("name", new Data("token1", "refreshToken2"));
        mapD.listData.add(new Data("token2", "refreshToken2"));
        //System.out.println(mapD);
        System.out.println(OM.writeValueAsString(mapD));
    }
    @Test
    public void fromJsonToMap() throws IOException {
        String jstr = "{\"map_data\":{\"name\":{\"token\":\"token1\",\"refresh_token\":\"refreshToken2\"}},\"list_data\":[{\"token\":\"token2\",\"refresh_token\":\"refreshToken2\"}]}";
        MapData mapD = OM.readValue(jstr, MapData.class);
        System.out.println(mapD);
    }
    @Test
    public void toJsonComplicatedData() throws JsonProcessingException {
        ComplicatedData cd = new ComplicatedData();
        cd.code = 1;
        cd.data = new Data("token3", "refresh_token3");
        //System.out.println(cd);
        System.out.println(OM.writeValueAsString(cd));


        ResultFacade<Data> rf = new ResultFacade<Data>();
        rf.setCode(1);
        rf.setMessage("OK");
        rf.setData(new Data("token1", "refreshToken1"));
        System.out.println(OM.writeValueAsString(rf));
    }
    @Test
    public void fromJsonToComplicatedData() throws IOException {
        String jstr = "{\"data\":{\"token\":\"token3\",\"refresh_token\":\"refresh_token3\"},\"code\":1}";
        ComplicatedData cd = OM.readValue(jstr, ComplicatedData.class);
        System.out.println(cd);


        //jstr = "{\"code\":0,\"message\":\"OK\",\"data\":{\"token\":\"token1\",\"refresh_token\":\"refreshToken1\"}}";
        jstr = "{\"message\":\"OK\",\"code\":0,\"data\":{\"token\":\"token1\",\"refresh_token\":\"refreshToken1\"}}";
        ResultFacade<Data> rf = OM.readValue(jstr, ResultFacade.class);
        System.out.println(rf);

    }

}
