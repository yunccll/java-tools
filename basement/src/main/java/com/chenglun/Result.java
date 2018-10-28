package com.chenglun;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Result {
    public static Result OK()
    {
        return new Result(0, "OK", null);
    }
    public static Result FAILED()
    {
        return new Result(-1, "Common Failed", null);
    }
    public static Result INTERNAL_ERROR()
    {
        return new Result(-2, "Internal Error", null);
    }

    private int code ;
    private String message;
    private Object data;
    public Result()
    {
        this(0, "OK", null);
    }
    public Result(final int code, final String message, final Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(final Result result) {
        this(result.code, result.message, result.data);
    }

    @JsonProperty("code")
    public int getCode(){
        return this.code;
    }
    public Result setCode(final int code)
    {
        if(this.code != code) {
            this.code = code;
        }
        return this;
    }

    @JsonProperty("message")
    public String getMessage(){
        return this.message;
    }
    public Result setMessage(final String message){
        if(!Util.equals(this, message)){
            this.message = message;
        }
        return this;
    }

    public Result setException(final Exception e){
        this.setMessage(e.toString());
        return this;
    }

    @JsonProperty("data")
    public Object getData(){
        return this.data;
    }
    public Result setData(final Object data){
        if(this.data != data){
            this.data = data;
        }
        return this;
    }
    public <T> T getDataAsType(){
        return (T)(this.getData());
    }
    public Map<String, Object> getDataAsMap(){
        return (Map<String, Object>)getData();
    }


    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if( !(obj instanceof  Result)){
           return false;
        }
        Result cobj =(Result)obj;
        return cobj.code == this.code
            && Util.equals(this.message, cobj.message)
            && Util.equals(this.data, cobj.data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("code:[%d] msg:[%s] data:[", this.code, this.message));
        if(this.data != null) {
            sb.append(this.data.toString());
        }
        else{
            sb.append("null");
        }
        sb.append(']');
        return sb.toString();
    }
}
