package com.chenglun.crypt;

import com.chenglun.util.Args;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DES3Crypt
{
    private static final String CryptoAlgorithm    = "DESede";
    private static final String DefaultGroupMode          = "ECB";
    private static final String DefaultPadingMethod       = "PKCS5Padding";
    private static final int KeyLen = 24;

    private final byte[] key;
    private final SecretKeySpec secretKey;
    private final String groupMode;
    private final String padingMethod;
    public DES3Crypt(final byte[] key) {
        this(key, DefaultGroupMode, DefaultPadingMethod);
    }

    private byte [] formatKey(final byte [] key){
        Args.assertNotNull(key, "key");
        byte [] realKey = new byte [KeyLen];
        for(int i = 0; i < realKey.length; ++i ){
            if( i < key.length ) {
                realKey[i] = key[i];
            }
            else{
                realKey[i] = 0;
            }
        }
        return realKey;
    }

    public DES3Crypt(final byte[] key, final String groupMode, final String paddingMethod){
        this.key = formatKey(key);
        this.secretKey = new SecretKeySpec(this.key, CryptoAlgorithm);

        this.groupMode = groupMode;
        this.padingMethod = paddingMethod;
    }


    public static String encrypt(final String key, final String data){
        return new DES3Crypt(key.getBytes()).encrypt(data);
    }
    public static String decrypt(final String key, final String data){
        return new DES3Crypt(key.getBytes()).decrypt(data);
    }

    public static byte [] encrypt(final byte [] key, final byte [] data){
        return new DES3Crypt(key).encrypt(data);
    }
    public static byte [] decrypt(final byte [] key, final byte [] data){
        return new DES3Crypt(key).decrypt(data);
    }


    public String encrypt(final String data){
        Args.assertNotNull(data, "data");
        return Hex.encodeHexString(crypt(Cipher.ENCRYPT_MODE, data.getBytes()));
    }
    public String decrypt(final String data) {
        Args.assertNotNull(data, "data");
        try {
            byte [] bdata = Hex.decodeHex(data);
            return new String(crypt(Cipher.DECRYPT_MODE, bdata));
        } catch (DecoderException e) {
            throw new CryptoException(String.format("decrypt data source not the hex string, data:[%s]", data), e);
        }
    }
    public byte [] encrypt(final byte [] data){
        Args.assertNotNull(data, "data");
        return crypt(Cipher.ENCRYPT_MODE, data);
    }
    public byte [] decrypt(final byte [] data){
        Args.assertNotNull(data, "data");
        return crypt(Cipher.DECRYPT_MODE, data);
    }

    private byte [] crypt(final int cryptMode, final byte [] data){
        Cipher c1 = null;
        try {
            c1 = Cipher.getInstance(CryptoAlgorithm + "/" + this.groupMode + "/" + this.padingMethod);
            c1.init(cryptMode, this.secretKey);
            return c1.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(String.format("crypto algorithm:[%s] error!", CryptoAlgorithm), e);
        } catch (NoSuchPaddingException e) {
            throw new CryptoException(String.format("crypto padding method:[%s] error!",this.groupMode), e);
        } catch (BadPaddingException e) {
            throw new CryptoException(String.format("crypto padding failed, use [%s]", this.padingMethod), e);
        } catch (IllegalBlockSizeException e) {
            throw new CryptoException(String.format("block size error, block_size:[%d]!", (c1 != null) ? c1.getBlockSize(): -1 ), e);
        } catch (InvalidKeyException e) {
            throw new CryptoException(String.format("key:[%s] invalid!", key), e);
        }
    }
}
