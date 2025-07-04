package min.gob.ec.tracingservices.util;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Crypt {
    @Getter @Setter
    private String cpw;
    @Getter @Setter
    private String csl;
    @Getter @Setter
    private IvParameterSpec ivParameterSpec;
    @Getter @Setter
    private SecretKey secretKey;
    private static final String PSW = "(G+KaPdSgVkYp3s6";

    public Crypt() throws Exception {
        this.cpw = PSW;
        this.getSecretKeys();
        this.getIv();
    }

    private void getSecretKeys() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(this.cpw.getBytes());
        keyGenerator.init(128, secureRandom);
        this.setSecretKey(keyGenerator.generateKey());
    }

    private void getIv() {
        byte[] bytes = this.cpw.getBytes();
        int l = 16;
        if (l == bytes.length) {
            this.setIvParameterSpec(new IvParameterSpec(bytes));
            return;
        }
        this.setIvParameterSpec(new IvParameterSpec(Arrays.copyOf(bytes, l)));
    }

    public String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivParameterSpec);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.ivParameterSpec);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }
}
