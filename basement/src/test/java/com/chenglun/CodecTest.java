package com.chenglun;

import com.chenglun.crypt.CryptoException;
import com.chenglun.crypt.DES3Crypt;
import com.chenglun.crypt.Signature;
import com.chenglun.util.ArgsUtil;
import org.apache.commons.codec.binary.Hex;
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

import static org.junit.Assert.assertArrayEquals;
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

    private String des3Encrypt(final String key, final String data){
        ArgsUtil.assertNotEmpty(key, "key");
        ArgsUtil.assertNotEmpty(data, "data");
        return des3(Cipher.ENCRYPT_MODE, key, data);
    }
    private String des3Decrypt(final String key, final String data){
        ArgsUtil.assertNotEmpty(key, "key");
        ArgsUtil.assertNotEmpty(data, "data");
        return des3(Cipher.DECRYPT_MODE, key, data);
    }
    private String des3(final int encryptMode, final String key, final String data){
        final String CryptoAlgorithm = "DESede";
        final String ECB            = "ECB";
        final String PadingMethod = "PKCS5Padding";

        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), CryptoAlgorithm);

        Cipher c1 = null;
        try {
            c1 = Cipher.getInstance(CryptoAlgorithm + "/" + ECB + "/" + PadingMethod);
            c1.init(Cipher.ENCRYPT_MODE, skey);
            return Hex.encodeHexString(c1.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(String.format("crypto algorithm:[%s] error!", CryptoAlgorithm), e);
        } catch (NoSuchPaddingException e) {
            throw new CryptoException(String.format("crypto padding method:[%s] error!",PadingMethod), e);
        } catch (BadPaddingException e) {
            throw new CryptoException(String.format("crypto padding failed, use [%s]", PadingMethod), e);
        } catch (IllegalBlockSizeException e) {
            throw new CryptoException(String.format("block size error, block_size:[%d]!", (c1 != null) ? c1.getBlockSize(): -1 ), e);
        } catch (InvalidKeyException e) {
            throw new CryptoException(String.format("key:[%s] invalid!", key), e);
        }
    }



    @Test
    public void tripleDESTest(){
        final String key = "01234567890123456789";
        final String data = "hello world";
        final String expectEncHexText = "71609a35946797161da7169c269ca725";

        DES3Crypt des = new DES3Crypt(key.getBytes());

        {
            // string --encrypt---> string --decrypt-> string
            String encHexText = des.encrypt(data);
            assertEquals(expectEncHexText, encHexText);

            String plainText = des.decrypt(encHexText);
            assertEquals(data, plainText);
        }

        {
            //string --encrypt --> string --decrypt--->string
            String encHexText = DES3Crypt.encrypt(key, data);
            String plainText = DES3Crypt.decrypt(key, encHexText);
            assertEquals(data, plainText);
        }


        {
            //bytes --encrypt --> bytes
            byte[] encBytes = des.encrypt(data.getBytes());
            //bytes --decrypt --> bytes
            byte[] decBytes = des.decrypt(encBytes);
            assertArrayEquals(data.getBytes(), decBytes);
        }

        {
            byte[] encBytes = DES3Crypt.encrypt(key.getBytes(), data.getBytes());
            byte[] plainBytes = DES3Crypt.decrypt(key.getBytes(), encBytes);
            assertArrayEquals(data.getBytes(), plainBytes);
        }
    }




    @Test
    public void signature()
    {
        String nonce = "1234567890";
        String timestamp = "1540823295344";
        String key = "123456789012345678901234";
        String phoneNo = "13761875234";
        String expect = "2d7990a1f042beb281a1f1f0012be3a10bc96291";


        Signature sign = Signature.create(key.getBytes());
        System.out.println(sign.toString());
        String signHex = sign.setTimestamp(timestamp)
                .setNonce(nonce)
                .hashFirst(phoneNo)
                .signature();
        assertEquals(expect, signHex);

        String hashPhoneNo = DigestUtils.sha1Hex(phoneNo);
        //System.out.println(hashPhoneNo);
        String encText = DES3Crypt.encrypt(key, hashPhoneNo);
        //System.out.println(encText);
        String result = DigestUtils.sha1Hex((encText + timestamp + nonce));
        assertEquals(expect, result);

        String phoneNo2 = "13636318920";
        signHex = sign
                .setTimestamp()
                .setNonce()
                .hashFirst("13358585693")
                .signature();
        System.out.println(signHex);
    }
}
