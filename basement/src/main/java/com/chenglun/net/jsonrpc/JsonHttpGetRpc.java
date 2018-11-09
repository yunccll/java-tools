package com.chenglun.net.jsonrpc;

import com.chenglun.net.Client;
import com.chenglun.net.Clients;
import com.chenglun.util.ArgsUtil;
import org.apache.http.client.methods.HttpGet;

public class JsonHttpGetRpc extends JsonHttpRpc
{
    //final static Logger log = LoggerFactory.getLogger(JsonHttpGetRpc.class);

    public static class Builder extends RpcBuilder<Builder, HttpGet>
    {
        public Builder() {
            super(new HttpGet());
        }
        public JsonHttpGetRpc build() {
            ArgsUtil.assertNotNull(this.httpMethod, "HttpGet");
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
