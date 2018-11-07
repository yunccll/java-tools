package com.chenglun.crypt;

import com.chenglun.util.Args;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class Signature {
    private byte [] key;
    private DES3Crypt des3;
    private String timestamp;
    private String nonce;
    private String data;

    public static Signature create(byte [] key){
        return new Signature(key);
    }

    private Signature(final byte [] key){
        Args.assertNotNull(key, "key");
        this.key = key;
        this.des3 = new DES3Crypt(key);
    }

    public Signature reset(){
        return this.setTimestamp().setNonce().setData();
    }

    public Signature setKey(final String key){
        return this.setKey(key.getBytes());
    }
    public Signature setKey(final byte [] key){
        this.key = key;
        return this;
    }

    public Signature setTimestamp(final long timeStamp){
        return this.setTimestamp(String.valueOf(timeStamp));
    }
    public Signature setTimestamp(){
        return this.setTimestamp(System.currentTimeMillis());
    }
    public Signature setTimestamp(final String timestamp){
        this.timestamp = timestamp;
        return this;
    }

    private Signature setNonce(final int nonce){
        return this.setNonce(String.valueOf(nonce));
    }
    public Signature setNonce(final String nonce){
        this.nonce = nonce;
        return this;
    }
    public Signature setNonce(){
        long nonce = new Random().nextLong();
        return this.setNonce(String.valueOf(nonce));
    }

    public Signature setData(final byte [] data){
        return setData(Hex.encodeHexString(data));
    }
    public Signature setData(final String data){
        this.data = data;
        return this;
    }
    public Signature setData(){
        return this.setData("");
    }
    public Signature hashFirst(final String data){
        this.data = DigestUtils.sha1Hex(data);
        return this;
    }

    public final byte [] getKey() {
        return this.key;
    }
    public final String getData() {
        return this.data;
    }
    public final String getTimestamp() {
        return this.timestamp;
    }
    public final String getNonce() {
        return this.nonce;
    }
    public String signature(){
        Args.assertNotNull(this.data, "data");
        Args.assertNotEmpty(this.timestamp, "timestamp");
        Args.assertNotEmpty(this.nonce, "nonce");
        String encHexString = this.des3.encrypt(this.data);
        //TODO: use high perf sha1Hex...
        return DigestUtils.sha1Hex( encHexString + this.timestamp + this.nonce);
    }
    @Override
    public String toString(){
        return String.format("data:[%s],timestamp:[%s],nonce:[%s]", this.data, this.timestamp, this.nonce);
    }
};
