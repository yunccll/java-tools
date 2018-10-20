package com.chenglun;

import java.util.Map;

public class Result {

    public static class Builder
    {

        public static Builder DEFAULT()
        {
            return new Builder(0, "OK", null);
        }
        public static Builder OK()
        {
            return new Builder(0, "OK", null);
        }
        public Builder()
        {
           this(0, "OK", null);
        }
        public Builder(final int code)
        {
            this(code, "OK", null);
        }
        public Builder(final int code, final String message)
        {
            this(code, message, null);
        }
        public Builder(final int code ,final Exception e){
            this(code, e.toString(), null);
        }
        public Builder(final int code, final String message, final Map<String, String> data){
            this._code = code;
            this._message = message;
            this._data = data;
        }

        private int _code ;
        private String _message;
        private Map<String, String> _data;

        public Result.Builder setData(final Map<String, String> data){
            this._data = data;
            return this;
        }
        public Result.Builder setCode(final int code){
            this._code = code;
            return  this;
        }

        public Result.Builder setMessage(final String message){
            this._message = message;
            return this;
        }

        public Result build(){
            return new Result(this._code, this._message, this._data);
        }

        public Result.Builder setException(final Exception e){
            return setMessage(e.toString()) ;
        }
    }

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

    private int _code ;
    private String _message;
    private Map<String, String> _data;
    public Result(final int code, final String message, final Map<String, String> data){
        this._code = code;
        this._message = message;
        this._data = data;
    }

    public Result(final Result result) {
        this(result._code, result._message, result._data);
    }

    public int getCode(){
        return this._code;
    }
    public String getMessage(){
        return this._message;
    }
    public Map<String, String> getData(){
        return this._data;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(obj == this) return true;
        if( !(obj instanceof  Result)){
           return false;
        }
        Result cobj =(Result)obj;
        return cobj._code == this._code 
            && Util.equals(this._message, cobj._message) 
            && Util.equals(this._data, cobj._data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("code:[%d] msg:[%s] data:[", this._code, this._message));
        if(this._data != null) {
            for (Map.Entry<String, String> e : this._data.entrySet()) {
                sb.append(String.format("%s:%s,", e.getKey(), e.getValue()));
            }
        }
        else{
            sb.append("null");
        }
        sb.append(']');

        return sb.toString();
    }
}
