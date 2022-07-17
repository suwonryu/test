package poodle;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Arrays;

public class PoodleAttackTest {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    //encMsg: 5939206bfa54c5c1530f0f73ae11fd3f
    //decMsg: 48656c6c6f2c20576f726c642121
    //Hello, World!!

    public static void main(String[] args) throws Exception {
        byte[] originIv = Hex.decode("4a9e2d91597f319b01deda67f219efde");
        byte[] encMsg = Hex.decode("5939206bfa54c5c1530f0f73ae11fd3f");
        byte[] iv = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        byte[] ivXorPt = new byte[16];

        for (int j = 0; j < 16; j++) {
            int attackIdx = 15 - j;

            for (int x = 0; x < j; x++) {
                iv[15 - x] = find(ivXorPt[15 - x], j + 1);
            }

            for (int i = 0; i < 256; i++) {
                try {
                    iv[attackIdx] = (byte) (i & 0xFF);
                    decrypt(iv, encMsg);
                } catch (Exception e) {
                    if (e.getMessage().equals("WrongMsg")) {
                        ivXorPt[attackIdx] = (byte) ((iv[attackIdx] ^ (j + 1)) & 0xFF);
                    }
                }
            }
        }

        System.out.println("ivXorPt: " + Hex.toHexString(ivXorPt));
        System.out.println("pt: " + Hex.toHexString(xor(originIv, ivXorPt)));


        byte[] fpt = new byte[16];
        byte[] myPy = "Goood, World!!".getBytes();
        System.arraycopy(myPy, 0, fpt, 0, myPy.length);
        fpt[14] = 0x02;
        fpt[15] = 0x02;

        byte[] fakeIv = xor(fpt, ivXorPt);

        String response = decrypt(fakeIv, encMsg);

        System.out.println(response);

    }

    static byte find(byte b, int i) {
        for (int j = 0; j < 256; j++) {
            if (((b ^ j) & 0xFF) == i) {
                return (byte) j;
            }
        }

        return 0x00;
    }

    static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) ((a[i] ^ b[i]) & 0xFF);
        }

        return result;
    }


    static String decrypt(byte[] iv, byte[] encMsg) throws Exception {
        byte[] originMsg = Hex.decode("48656c6c6f2c20576f726c642121");
        try {
            byte[] key = Hex.decode("31d2fe038ff627819b6914c5413777e6");
            // byte[] iv = Hex.decode("4a9e2d91597f319b01deda67f219efde");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
            byte[] decMsg = cipher.doFinal(encMsg);

            if (new String(decMsg).equals("Goood, World!!")) {
                return "Good";
            }

            if (!Arrays.equals(originMsg, decMsg)) {
                throw new Exception("WrongMsg");
            }

            return new String(decMsg);
        } catch (BadPaddingException badPaddingException) {
            throw new Exception("BadPadding");
        }
    }
}
