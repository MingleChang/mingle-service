package mingle.chang.service.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtils {
    private static final String ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }
    public static PublicKey publicKey(String base64) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64);
        return publicKey(bytes);
    }
    public static PublicKey publicKey(byte[] bytes) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    public static PrivateKey privateKey(String base64) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64);
        return privateKey(bytes);
    }
    public static PrivateKey privateKey(byte[] bytes) throws Exception{

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
    public static byte[] encrypt(byte[] bytes, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(bytes);
    }
    public static byte[] decrypt(byte[] bytes, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(bytes);
    }
    public static byte[] sign(byte[] bytes, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(bytes);
        return signature.sign();
    }
    public static boolean verify(byte[] bytes, byte[] signatureBytes, PublicKey publicKey) throws Exception {
        Signature verify = Signature.getInstance(SIGNATURE_ALGORITHM);
        verify.initVerify(publicKey);
        verify.update(bytes);
        return verify.verify(signatureBytes);
    }
}
