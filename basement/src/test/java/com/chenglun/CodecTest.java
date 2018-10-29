package com.chenglun;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class CodecTest {

    @Test
    public void md5()
    {
        String data = "hello";
        //5d41402abc4b2a76b9719d911017c592
        String result = DigestUtils.md5Hex(data);

        byte [] bdata = data.getBytes();
        String resultByte = DigestUtils.md5Hex(bdata);
        assertEquals(result, resultByte);
    }
    @Test
    public void sha1()
    {
        {
            String data = "hello";
            //aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d
            String result = DigestUtils.sha1Hex(data);
            System.out.println(result);

            byte [] bdata = data.getBytes();
            String resultByte = DigestUtils.sha1Hex(bdata);
            assertEquals(result, resultByte);

        }

        {
            MessageDigest dig = null;
            try {
                dig = MessageDigest.getInstance("sha1");
                byte [] result = dig.digest("hello".getBytes());
                System.out.println(Hex.encodeHexString(result));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    private String dec3(String key, String data){
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "DESede");
        try {
            Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            c1.init(Cipher.ENCRYPT_MODE, skey);
            return Hex.encodeHexString(c1.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void tripleDESTest(){
        String key = "012345678901234567890123";
        String data = "hello world";
        String encText = dec3(key, data);
        System.out.println(encText);
    }

    @Test
    public void signature()
    {
        String nonce = "1234567890";
        String timestamp = "1540823295344";
        String key = "123456789012345678901234";
        String phoneNo = "13761875234";
        String expect = "6478a700543d1f0a624ffd25515695502899c6a9";



        String hashPhoneNo = DigestUtils.sha1Hex(phoneNo);
        System.out.println(hashPhoneNo);
        String encText = dec3(key, hashPhoneNo);
        System.out.println(encText);
        String result = DigestUtils.sha1Hex((encText + timestamp + nonce));
        System.out.println(result);
        //#assertEquals(expect, result);


    }
}
