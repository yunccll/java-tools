package com.chenglun;

import com.chenglun.crypt.Signature;
import com.chenglun.net.jsonrpc.JsonRpcFacade;
import com.chenglun.util.Args;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class TeacherAppTest {
    public TeacherAppTest(){}

    public static interface IResult{
        public int getCode();
        public String getMessage();
    }

    public static class AbstractResult implements  IResult {
        public AbstractResult(){
            this.code = 0;
            this.message = "OK";
        }
        public AbstractResult(final int code, final String message){
            this.code = code;
            this.message = message;
        }
        private int code;
        @Override
        @JsonProperty("code")
        public int getCode(){
            return this.code;
        }
        public void setCode(final int code){
            this.code = code;
        }
        private String message;
        @Override
        @JsonProperty("message")
        public String getMessage(){
            return this.message;
        }
        public void setMessage(final String message){
            this.message = message;
        }
        @Override
        public String toString(){
            return String.format("code:%d, message:%s",this.code, this.message);
        }
    }


    public static class TeacherApp {
        private static final String serverUrl = "http://dev-sh.admin.haotuoguan.cn/teacher";
        //private static final String serverUrl = "http://106.75.120.51/teacher";
        private static final String APP_KEY = "123456789012345678901234";


        private String phoneNo;
        private Signature sign;
        public Signature getSignature(){
            return this.sign;
        }
        private TeacherApp(final String phoneNo) {
            this.phoneNo = phoneNo;
            this.sign = Signature.create(APP_KEY.getBytes());
        }

        public static TeacherApp create(final String phoneNo) {
            return new TeacherApp(phoneNo);
        }


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

        public IResult signIn(final String password, Class<? extends IResult> cls) {
            class SignIn {
                private Signature sign;

                public SignIn(final String phoneNo, final String password, Signature sign) {
                    Args.assertNotEmpty(phoneNo, "phoneNo");
                    this.phoneNo = phoneNo;
                    Args.assertNotEmpty(password, "password");
                    this.password = password;

                    this.sign = sign.reset().hashFirst(password);
                }

                @JsonIgnore
                private String password;

                @JsonProperty("phone_no")
                private String phoneNo;

                public String getPhoneNo() {
                    return this.phoneNo;
                }

                @JsonProperty("timestamp")
                public long getTimestamp() {
                    return Long.parseLong(this.sign.getTimestamp());
                }

                @JsonProperty("nonce")
                public String getNonce() {
                    return this.sign.getNonce();
                }

                @JsonProperty("signature")
                public String getSignature() {
                    return this.sign.signature();
                }
            }
            SignIn si = new SignIn(this.phoneNo, password, this.getSignature());
            String url = serverUrl + "/signin";
            return TeacherApp.post(url, si, cls);
        }

        public IResult vcode(Class<? extends  IResult> cls) {
            class TeacherJsonView {
                private Signature sign;
                public TeacherJsonView(final String phoneNo, Signature sign) {
                    Args.assertNotEmpty(phoneNo, "phoneNo");
                    this.phoneNo = phoneNo;
                    this.sign = sign.reset().hashFirst(phoneNo);
                }

                @JsonProperty("phone_no")
                private String phoneNo;

                public String getPhoneNo() {
                    return this.phoneNo;
                }

                @JsonProperty("timestamp")
                public long getTimestamp() {
                    return Long.parseLong(this.sign.getTimestamp());
                }

                @JsonProperty("nonce")
                public String getNonce() {
                    return this.sign.getNonce();
                }

                @JsonProperty("signature")
                public String getSignature() {
                    return this.sign.signature();
                }
            }
            return post(serverUrl + "/vcode", new TeacherJsonView(this.phoneNo, this.getSignature()), cls);
        }
        public IResult signInVcode(final String vcode, Class<? extends  IResult> cls){
            class TeacherJsonView {
                private Signature sign;
                public TeacherJsonView(final String phoneNo,  final String vcode, Signature sign) {
                    Args.assertNotEmpty(phoneNo, "phoneNo");
                    this.phoneNo = phoneNo;
                    this.sign = sign.reset().hashFirst(vcode);
                }

                @JsonProperty("phone_no")
                private String phoneNo;
                public String getPhoneNo() {
                    return this.phoneNo;
                }

                @JsonProperty("timestamp")
                public long getTimestamp() {
                    return Long.parseLong(this.sign.getTimestamp());
                }

                @JsonProperty("nonce")
                public String getNonce() {
                    return this.sign.getNonce();
                }

                @JsonProperty("signature")
                public String getSignature() {
                    return this.sign.signature();
                }
            }
            return post(serverUrl + "/signin/vcode", new TeacherJsonView(this.phoneNo, vcode, this.getSignature()), cls);
        }

    }
    static class SignInResult extends AbstractResult {
        public SignInResult(){
            super();
        }
        class Data {
            public Data(){}
            class Teacher {
                public Teacher(){}
                @JsonProperty("id")
                private int id;
                @JsonProperty("name")
                private String name;
                @Override
                public String toString(){
                    return "id:" + this.id + ", name:" + this.name;
                }
            }
            @JsonProperty("token")
            private String token;
            @JsonProperty("refresh_token")
            private String refreshToken;
            @JsonProperty("teacher")
            private Teacher teacher;
            @Override
            public String toString(){
                return String.format("token:%s,refresh_token:%s,teacher:%s", this.token, this.refreshToken, this.teacher);
            }
        }

        public SignInResult(final int code, final String message, final Data data){
            super(code, message);
            this.data = data;
        }

        @JsonProperty("data")
        private Data data;
        public Data getData(){
            return this.data;
        }
        @Override
        public String toString(){
            return String.format("%s:data:%s", super.toString(), this.data);
        }
    }
    @Test
    public void signInTest() {
        IResult res = TeacherApp.create("15821785043").signIn("123456", SignInResult.class);
        assertTrue(res.getCode() == 0);
        if(res.getCode() == 0){
            System.out.println(res.getClass().getName());
        }
        else{
            System.out.println(res);
        }
    }
/*
    @Test
    public void vcodeTest() {
        ResultFacade<String> res = TeacherApp.create("13761875234").vcode();
        System.out.println(res.toString());
        assertTrue(res.getCode() == 0);
    }


    @Test
    public void signInVcodeTest() {

        TeacherApp app = TeacherApp.create("15821785043");
        {
            ResultFacade<String> res = app.vcode();
            assertTrue(res.getCode() == 0);
        }
        {
            ResultFacade<String> res = app.signInVcode("343313");
            assertTrue(res.getCode() == 0);
        }
    }

    @Test
    public void signOutTest()
    {
        Result res = TeacherApp.createSignIned("13500003333", "123456").signOut();
        System.out.println(res.toString());
        assertTrue(res.getCode() == 0);
    }
    @Test
    public void showClassStudents()
    {
        Result res = TeacherApp.createSignIned("13500003333", "123456").showClassStudents();
        System.out.println(res.toString());
        assertTrue(res.getCode() == 0);
    }
    */
}
