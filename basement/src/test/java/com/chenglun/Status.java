package com.chenglun;

public class Status<T> {
    private int _code;
    private String _message;
    private T _data;

    public Status(final int code, final String message, final T data){
        this._code = code;
        this._message = message;
        this._data = data;
    }

    public int getCode()
    {
        return this._code;
    }
    public String getMessage()
    {
        return this._message;
    }
    public T getData(){
        return this._data;
    }

    @Override
    public String toString()
    {
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


