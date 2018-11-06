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


        private static <T, R> R post(String url, T obj, Class<R> cls) {
            try {
                return JsonRpcFacade.post(url, obj, cls);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }

        private static <T> Result post(String url, T obj) {
            return post(url, obj, Result.class);
        }



        public Result vcode() {
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
            return post(serverUrl + "/vcode", new TeacherJsonView(this.phoneNo, this.getSignature()));
        }

        public Result signIn(final String password) {
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
            return post(serverUrl + "/signin", new SignIn(this.phoneNo, password, this.getSignature()));
        }

        public Result signInVcode(final String vcode) {
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
            return post(serverUrl + "/signin/vcode", new TeacherJsonView(this.phoneNo, vcode, this.getSignature()));
        }
    }

    @Test
    public void vcodeTest() {
        Result res = TeacherApp.create("13761875234").vcode();
        System.out.println(res.toString());
        assertTrue(res.getCode() == 0);
    }

    @Test
    public void signInTest() {
        Result res = TeacherApp.create("15821785043").signIn("123456");
        System.out.println(res.toString());
        assertTrue(res.getCode() == 0);
    }

    @Test
    public void signInVcodeTest() {

        TeacherApp app = TeacherApp.create("15821785043");
        {
            Result res = app.vcode();
            assertTrue(res.getCode() == 0);
        }
        {
            Result res = app.signInVcode("343313");
            assertTrue(res.getCode() == 0);
        }
    }

     /*
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
