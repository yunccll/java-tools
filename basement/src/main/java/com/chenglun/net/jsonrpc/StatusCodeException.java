package com.chenglun.net.jsonrpc;

public class StatusCodeException extends RuntimeException {
    public StatusCodeException(int statusCode, String s) {
        super(String.format("status_code:%d, info:%s", statusCode, s));
    }
}
