package com.chenglun.app.teacher;

import com.chenglun.crypt.Signature;
import com.chenglun.net.jsonrpc.JsonRpcFacade;
import com.chenglun.util.AbstractResult;
import com.chenglun.util.ArgsUtil;
import com.chenglun.util.IResult;
import com.chenglun.util.Result;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class SignIner {
    final static Logger log = LoggerFactory.getLogger(SignIner.class);

    private static <T, R> R post(String url, T obj, Class<R> clsReturn) {
        try {
            return JsonRpcFacade.post(url, obj, clsReturn);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final Signature signature;
    private String phoneNo ;

    private String token ;
    private String refreshToken;

    private boolean isLogIn;


    public SignIner(final String signatureKey, final String phoneNo ){
        ArgsUtil.assertNotEmpty(signatureKey, "signatureKey for logger");
        this.signature = Signature.create(signatureKey.getBytes());

        ArgsUtil.assertNotEmpty(phoneNo, "phoneNo");
        this.phoneNo = phoneNo;
    }
    public boolean isLogIn(){
        return isLogIn;
    }
    public boolean isLogOut(){
        return !isLogIn();
    }


    public static class LoginResult extends AbstractResult {
        public LoginResult(){ super(); }
        class Data {
            public Data() {}
            @JsonProperty("token")
            public String token;
            @JsonProperty("refresh_token")
            public String refreshToken;
            @JsonProperty("teacher")
            private Teacher teacher;
            public Teacher getTeacher(){
                return this.teacher;
            }
            @Override
            public String toString() { return String.format("token:%s,refresh_token:%s,teacher:%s", this.token, this.refreshToken, this.teacher); }
        }
        @JsonProperty("data")
        private Data data;
        public  Data getData(){
            return this.data;
        }
    }

    private IResult signaturePost(String hashData, String url, Class<? extends IResult> clsReturn) {
        String signTexT = this.signature.reset().hashFirst(hashData).signature();
        class SignaturePostReq {
            public SignaturePostReq(final String phoneNo, final String timestamp, final String nonce, final String signatureText
            ){
                this.phoneNo = phoneNo;
                this.timestamp = Long.parseLong(timestamp);
                this.nonce = nonce;
                this.signatureText = signatureText;
            }
            @JsonProperty("phone_no")
            public String phoneNo;
            @JsonProperty("timestamp")
            public long timestamp;
            @JsonProperty("nonce")
            public String nonce;
            @JsonProperty("signature")
            public String signatureText;
        }
        SignaturePostReq si = new SignaturePostReq(this.phoneNo, this.signature.getTimestamp(), this.signature.getNonce(), signTexT);
        IResult res = post(url, si, clsReturn);
        if(!res.isOK()){
            log.error(String.format("send signature post failed, details:%s", res));
        }
        return res;
    }

    public IResult logInPassword(final String password, final String url){
        ArgsUtil.assertNotEmpty(password, String.format("password of " + this.phoneNo));
        ArgsUtil.assertNotEmpty(url, "url for loginPassword");

        IResult res = signaturePost(password, url, LoginResult.class);
        if(res.isOK()){
            LoginResult rsp = (LoginResult)res;
            this.token = rsp.data.token;
            this.refreshToken = rsp.data.refreshToken;
            this.isLogIn = true;
        }
        else{
            log.debug("login failed, cause:%s", res);
        }
        return res;
    }

    public boolean sendVerifyCode(final String url){
        return signaturePost(this.phoneNo, url, Result.class).isOK();
    }

    public boolean logInVerifyCode(final String vcode, final String url){
        return signaturePost(vcode, url, Result.class).isOK();
    }
    public boolean logout(final int teacherId, final String url){
        ArgsUtil.assertNotEmpty(url, "url for logOut");
        class TeacherTokenView {
            public TeacherTokenView(final int teacherId, final String token){
                this.teacherId = teacherId;
                this.token = token;
            }
            private int teacherId;
            @JsonProperty("teacher_id")
            public int getId(){ return this.teacherId;}
            @JsonProperty("token")
            private String token;
            public String getToken(){return this.token;}
        }
        IResult res = post(url, new TeacherTokenView(teacherId, this.token), Result.class);
        if(res.isOK()) {
            this.isLogIn = false;
            return true;
        }
        log.error(String.format("logout failed:%s", res));
        return  false;
    }

}
