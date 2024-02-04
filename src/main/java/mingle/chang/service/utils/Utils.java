package mingle.chang.service.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Utils {
    public static void main(String[] args) {
        String a = "oss-cn-chengdu";
        System.out.println(a);
        a = encrypt(a);
        System.out.println(a);
        a = decrypt(a);
        System.out.println(a);
    }
    public static byte[] covert(byte[] bytes) {
        int pIA1 = 0x2DB12EE;
        int pIC1 = 0x013A85F;
        int mKey = 0x534CA75;
        int idKey = 0x2EAD25A;

        int key0=mKey;
        int id0=idKey;
        for (int i = 0; i < bytes.length; ++i) {
            key0 = pIA1 * (key0 % id0) + pIC1;
            bytes[i] ^= ((key0 >> 15) + 0xe3) & 0xFF;
        }
        return bytes;
    }
    public static String encrypt(String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        bytes = covert(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
    public static String decrypt(String content) {
        byte[] bytes = Base64.getDecoder().decode(content);
        bytes = covert(bytes);
        return new String(bytes);
    }
}
