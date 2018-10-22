package com.chenglun;

import javax.management.relation.RelationNotFoundException;

public class ErrorResponseException extends RuntimeException {
    public ErrorResponseException(String s) {
        super(s);
    }

    //TODO:
    public static class CodeException extends RuntimeException {
        private int _code;
        public int getCode(){
            return this._code;
        }
        public CodeException(final int code, String s){
            super("code:[" + String.valueOf(code) + "],err_msg:[" + s + "]");
        }
    }

    public static class ServerSideException extends  CodeException
    {
        public ServerSideException(String s) {
            super(-100, s);
        }
    }
    public static class ClientSideException extends  CodeException
    {
        public ClientSideException(String s) {
            super(-200, s);
        }
    }
    public static class BizException extends CodeException
    {
        public BizException(String s) {
            super(-300, s);
        }
    }
    public static class CommonException extends  CodeException
    {
        public CommonException(String s) {
            super(-50, s);
        }
    }
}
