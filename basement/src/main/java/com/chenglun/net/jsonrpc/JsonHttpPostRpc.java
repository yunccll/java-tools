package com.chenglun.net.jsonrpc;

import com.chenglun.net.Client;
import com.chenglun.net.Clients;
import com.chenglun.util.ArgsUtil;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

public class JsonHttpPostRpc extends  JsonHttpRpc
{
    // final static Logger log = LoggerFactory.getLogger(JsonHttpPostRpc.class);

    public static class Builder extends  RpcBuilder<Builder, HttpPost>{
        public Builder(){
            super(new HttpPost());
        }

        private String json;
        public JsonHttpPostRpc build() {
            ArgsUtil.assertNotNull(this.httpMethod, "HttpPost");
            return new JsonHttpPostRpc(this.client, this.httpMethod, this.json);
        }

        public JsonHttpPostRpc.Builder setJson(final String json){
            this.json = json;
            return this;
        }

        public static JsonHttpPostRpc.Builder createDefault(){
            return new JsonHttpPostRpc.Builder().setClient(Clients.createDefault());
        }
    }

    private String json;
    public JsonHttpPostRpc(final Client client, final HttpPost httpPost, final String json)
    {
        super(client, httpPost);
        this.json = json;
    }

    private void prepareRequestEntity(String json) {
        ArgsUtil.assertNotNull(this.httpRequest, "HttpPost");
        StringEntity entity = new StringEntity(ArgsUtil.isEmpty(json) ? "": json, ContentType.APPLICATION_JSON);
        ((HttpPost)this.httpRequest).setEntity(entity);
    }

    public String call() throws IOException
    {
        prepareRequestEntity(json);
        return super.call();
    }
}
