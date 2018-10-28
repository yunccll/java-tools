package com.chenglun;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
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

    public static class TeacherApp
    {
        private static final String serverUrl = "http://dev-sh.admin.haotuoguan.cn/teacher";

        private String phoneNo;
        private TeacherApp(final String phoneNo){
            this.phoneNo = phoneNo;
        }
        public static TeacherApp create(final String phoneNo)
        {
            return new TeacherApp(phoneNo);
        }


        private static <T,R > R post(String url, T obj, Class<R> cls){
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
        private  static <T> Result post(String url, T obj){
            return post(url, obj, Result.class);
        }

        static class TeacherJsonView {
            public TeacherJsonView(final String phoneNo)
            {
                Args.assertNotEmpty(phoneNo, "phoneNo");
                this.phoneNo = phoneNo;
            }
            @JsonProperty("phone_no")
            private String phoneNo;
            public String getPhoneNo()
            {
                return this.phoneNo;
            }

            @JsonProperty("timestamp")
            public long getTimestamp()
            {
                return 2222123333L;
            }
            @JsonProperty("nonce")
            public String getNonce()
            {
                return "222222222";
            }
            @JsonProperty("signature")
            public String getSignature()
            {
                //TODO:
                return "11111";
            }
        }
        public Result vcode(){
            return post(serverUrl + "/vcode", new TeacherJsonView(this.phoneNo));
        }

        public Result signIn(final String password)
        {
            class SignIn {
                public SignIn(final String phoneNo, final String password)
                {
                    Args.assertNotEmpty(phoneNo, "phoneNo");
                    this.phoneNo = phoneNo;
                    Args.assertNotEmpty(password, "password");
                    this.password = password;
                }
                @JsonIgnore
                private String password;

                @JsonProperty("phone_no")
                private String phoneNo;
                public String getPhoneNo()
                {
                    return this.phoneNo;
                }

                @JsonProperty("timestamp")
                public long getTimestamp()
                {
                    return 2222123333L;
                }
                @JsonProperty("nonce")
                public String getNonce()
                {
                    return "222222222";
                }
                @JsonProperty("signature")
                public String getSignature()
                {
                    //TODO:
                    return "11111";
                }
            }
            return post(serverUrl + "/signin", new SignIn(this.phoneNo, password));
        }

        public Result signInVcode() {
            return post(serverUrl + "/signin/vcode", new TeacherJsonView(this.phoneNo));
        }
    }

    @Test
    public void vcodeTest()
    {
        Result res = TeacherApp.create("13533330000").vcode();
        System.out.println(res.toString());
        assertTrue(res.getCode() == 8304);
    }

    @Test
    public void signInTest()
    {
        Result res = TeacherApp.create("135000033333").signIn("123456");
        System.out.println(res.toString());
        assertTrue(res.getCode() == 8302);
    }

    @Test
    public void signInVcodeTest()
    {
        Result res = TeacherApp.create("13500003333").signInVcode();
        System.out.println(res.toString());
        assertTrue(res.getCode() == 8319);
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
