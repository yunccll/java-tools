package com.chenglun;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHttpGetRpc extends JsonHttpRpc
{
    //final static Logger logger = LoggerFactory.getLogger(JsonHttpGetRpc.class);

    public static class Builder extends  RpcBuilder<Builder, HttpGet>
    {
        public Builder() {
            super(new HttpGet());
        }
        public JsonHttpGetRpc build() {
            Args.assertNotNull(this.httpMethod, "HttpGet");
            return new JsonHttpGetRpc(this.client, this.httpMethod);
        }

        public static Builder createDefault(){
            return new JsonHttpGetRpc.Builder().setClient(Clients.createDefault());
        }
    }


    private JsonHttpGetRpc(final Client client, final HttpGet httpGet) {
        super(client, httpGet);
    }

}
