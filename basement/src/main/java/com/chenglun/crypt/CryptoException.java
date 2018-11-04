package com.chenglun.crypt;

public class CryptoException extends RuntimeException{
    public CryptoException(String newMessage, Throwable cause){
        super(newMessage, cause);
    }
}
