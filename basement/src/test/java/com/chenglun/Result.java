package com.chenglun;


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

    private int _code ;
    private String _message;
    private Object _data;
    public Result()
    {
        this(0, "OK", null);
    }
    public Result(final int code, final String message, final Object data){
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
    public Result setCode(final int code)
    {
        if(this._code != code) {
            this._code = code;
        }
        return this;
    }

    public String getMessage(){
        return this._message;
    }
    public Result setMessage(final String message){
        if(!Util.equals(this, message)){
            this._message = message;
        }
        return this;
    }

    public Result setException(final Exception e){
        this.setMessage(e.toString());
        return this;
    }

    public Object getData(){
        return this._data;
    }
    public Result setData(final Object data){
        if(this._data != data){
            this._data = data;
        }
        return this;
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
            sb.append(this._data.toString());
        }
        else{
            sb.append("null");
        }
        sb.append(']');
        return sb.toString();
    }
}
