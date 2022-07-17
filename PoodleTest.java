package poodle;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;

public class PoodleTest {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws Exception {
        byte[] key = Hex.decode("31d2fe038ff627819b6914c5413777e6");
        byte[] iv = Hex.decode("4a9e2d91597f319b01deda67f219efde");
        byte[] msg = "Hello, World!!".getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        byte[] encMsg = cipher.doFinal(msg);

        System.out.println("encMsg: " + Hex.toHexString(encMsg));


        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        byte[] decMsg = cipher.doFinal(encMsg);

        System.out.println("decMsg: " + Hex.toHexString(decMsg));
        System.out.println(new String(decMsg, StandardCharsets.UTF_8));
    }
}
