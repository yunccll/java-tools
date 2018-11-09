package com.chenglun.app.teacher;

import com.chenglun.util.ArgsUtil;
import com.chenglun.util.IResult;
import com.sun.org.apache.xpath.internal.Arg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeacherApp {
    final static Logger log = LoggerFactory.getLogger(TeacherApp.class);



    public static TeacherApp create(final String phoneNo, final String appKey, final String serverUrl) {
        return new TeacherApp(phoneNo, appKey, serverUrl);
    }

    private final String phoneNo;
    private final String appKey;
    private final String serverUrl;
    private SignIner signIner;
    private TeacherApp(final String phoneNo, final String appKey, final String serverUrl) {
        this.phoneNo = phoneNo;
        this.appKey = appKey;
        this.serverUrl = serverUrl;
        this.signIner = new SignIner(appKey, this.phoneNo);
    }

    public boolean isLogin(){return this.signIner.isLogIn();}

    private Teacher teacher;
    public boolean logInPassword(final String password) {

        IResult res = this.signIner.logInPassword(password, serverUrl + "/signin");
        if (res.isOK()&& this.signIner.isLogIn()) {
            SignIner.LoginResult loginResult = (SignIner.LoginResult)res;
            this.teacher = loginResult.getData().getTeacher();
            return true;
        }

        log.error(String.format("sign in with password failed, cause: phoneNO:%s, %s", this.phoneNo, res));
        return false;
    }
    public boolean logOut(){
        if(this.teacher != null){
            return this.signIner.logout(this.teacher.getId(), serverUrl + "/signout");
        }

        log.error(String.format("login first, info: phoneNo:%s", this.phoneNo));
        return false;
    }
    public boolean sendVerifyCode(){
        ArgsUtil.assertNotNull(this.signIner, String.format("signIner of %s", this.phoneNo));
        return this.signIner.sendVerifyCode(serverUrl + "/vcode");
    }
    public boolean logInVerifyCode(final String vcode){
        ArgsUtil.assertNotNull(vcode, "verify code");
        return this.signIner.logInVerifyCode(vcode, serverUrl + "/signin/vcode");
    }
}
