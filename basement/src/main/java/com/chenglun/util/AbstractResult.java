package com.chenglun.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractResult implements  IResult{
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

    public boolean isOK(){
        return this.getCode() == 0;
    }

    @Override
    public String toString(){
        return String.format("code:%d, message:%s",this.code, this.message);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if( !(obj instanceof  Result)){
            return false;
        }
        AbstractResult cobj =(AbstractResult)obj;
        return cobj.code == this.code
                && Util.equals(this.message, cobj.message);
    }
}
