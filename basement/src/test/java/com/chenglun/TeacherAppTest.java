package com.chenglun;

import com.chenglun.app.teacher.TeacherApp;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TeacherAppTest {

    public TeacherAppTest(){}
    private static final String APP_KEY = "123456789012345678901234";
    private static final String SERVER_URL = "http://dev-sh.admin.haotuoguan.cn/teacher";
    //private static final String serverUrl = "http://106.75.120.51/teacher";

    @Test
    public void signInOut() {
        TeacherApp app = TeacherApp.create("15821785043", APP_KEY, SERVER_URL);
        assertTrue(app.logInPassword("123456"));

        assertTrue(app.logOut());
        assertTrue(app.isLogin() == false);
    }
    @Test
    public void sendVcode() {
        {
            TeacherApp app = TeacherApp.create("13761875234", APP_KEY, SERVER_URL);
            assertTrue(app.isLogin()  || app.isLogin() == false);
            assertTrue(app.sendVerifyCode() == false);
        }
        {
            TeacherApp app = TeacherApp.create("15821785043", APP_KEY, SERVER_URL);
            assertTrue(app.isLogin()  || app.isLogin() == false);
            assertTrue(app.sendVerifyCode());
        }
    }
    @Test
    public void signInVcode() {
        TeacherApp app = TeacherApp.create("15821785043", APP_KEY, SERVER_URL);
        assertTrue(app.sendVerifyCode());

        app.logInVerifyCode("");
    }

/*
    @Test
    public void showClassStudents()
    {
        Result res = TeacherApp.createSignIned("13500003333", "123456").showClassStudents();
        System.out.println(res.toString());
        assertTrue(res.getCode() == 0);
    }
    */
}
