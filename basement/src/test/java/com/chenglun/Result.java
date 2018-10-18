package com.chenglun;

import java.util.Map;

public class Result {

    public static class Builder
    {
        public Builder()
        {
           this(0, "OK", null);
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
    }

    //TODO: do better
    public static class ExceptionBuilder extends Builder
    {
        public ExceptionBuilder(final int code){
            super(code, "OK", null);
        }
        public ExceptionBuilder(final int code, Exception e)
        {
            super(code, e.toString(), null);
        }
        public Result.Builder setException(Exception e)
        {
            return setMessage(e.toString()) ;
        }
    }

    public static final Builder OK, Failed, InternalError;
    static {
        OK = new Builder(0, "OK", null);
        Failed = new Builder(-1, "Common Failed", null);
        InternalError = new Builder(-2, "Internal Error", null);
    }

    private int _code ;
    private String _message;
    private Map<String, String> _data;
    public Result(final int code, final String message, final Map<String, String> data){
        this._code = code;
        this._message = message;
        this._data = data;
    }

    public Result(final int code, final Exception e){
        this(code, e.toString(), null);
    }
    public Result(final int code, Exception e, final String msg){
        this(code, String.format("%s:%s", msg, e), null);
    }
    public Result(final Result result) {
        this(result._code, result._message, result._data);
    }


    public Result setData(final Map<String, String> data){
        this._data = data;
        return this;
    }
    public Map<String, String> getData(){
        return this._data;
    }
    public int getCode(){
        return this._code;
    }
    public Result setCode(final int code){
        this._code = code;
        return  this;
    }

    public String getMessage(){
        return this._message;
    }
    public Result setMessage(final String message){
        this._message = message;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb =
            new StringBuilder(String.format("code:[%d] msg:[%s] data:[", this._code, this._message));
        if(this._data != null) {
            for (Map.Entry<String, String> e : this._data.entrySet()) {
                sb.append(String.format("%s:%s,", e.getKey(), e.getValue()));
            }
        }
        sb.append(']');

        return sb.toString();
    }
}
