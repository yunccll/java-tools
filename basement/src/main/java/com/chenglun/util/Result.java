package com.chenglun.util;


import java.util.Map;

public class Result implements  IResult{
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

    private AbstractResult result;
    private Object data;
    public Result()
    {
        this(0, "OK", null);
    }
    public Result(final Result result) {
        this(result.getCode(), result.getMessage(), result.data);
    }
    public Result(final int code, final String message, final Object data){
        this.result = new AbstractResult(code, message);
        this.data = data;
    }

    @Override
    public int getCode(){
        return this.result.getCode();
    }
    public Result setCode(final int code)
    {
        this.result.setCode(code);
        return this;
    }
    @Override
    public String getMessage(){
        return this.result.getMessage();
    }
    public Result setMessage(final String message){
        this.result.setMessage(message);
        return this;
    }

    public boolean isOK(){
        return this.getCode() == 0;
    }

    public Result setException(final Exception e){
        this.setMessage(e.toString());
        return this;
    }

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
        return Util.equals(this.result, cobj.result)
            && Util.equals(this.data, cobj.data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.result.toString());

        sb.append(", data:[");
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
