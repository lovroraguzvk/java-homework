package hr.fer.oprpp1.hw05.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static hr.fer.oprpp1.hw05.crypto.Util.bytetohex;
import static hr.fer.oprpp1.hw05.crypto.Util.hextobyte;

public class Crypto {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Scanner scanner = new Scanner(System.in);
        if (Objects.equals(args[0], "checksha")) {
            if (args.length != 2) throw new IllegalArgumentException("Number of arguments must be 2");

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] computed = new byte[0];

            System.out.printf("Please provide expected sha-256 digest for %s:\n> ", args[1]);
            String expectedSHA = scanner.next();

            try (InputStream is = Files.newInputStream(Path.of(args[1]))) {
                byte[] buff = new byte[4096];
                while (true) {
                    int r = is.read(buff);
                    if(r == -1) break;
                    digest.update(buff, 0, r);
                }
                computed = digest.digest();

            } catch (IOException e) {
                System.out.printf("File \"%s\" not found, or something went wrong with it", args[1]);
            }

            if(computed.length != 0 && bytetohex(computed).equals(expectedSHA)) {
                System.out.printf("Digesting completed. Digest of %s matches expected digest.", args[1]);
            } else {
                System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s", args[1], bytetohex(computed));
            }
            return;
        }



        if (args.length != 3) throw new IllegalArgumentException("Number of arguments must be 3");

        boolean encrypt = Objects.equals(args[0], "encrypt");

        System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
        String keyText = scanner.next();
        while(keyText.length() != 32 && keyText.matches("^[a-fA-F0-9]+$")) {
            System.out.print("Password length must be 32 hex-digits. Please try again:\n> ");
            keyText = scanner.next();
        }
        System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
        String ivText = scanner.next();
        while(ivText.length() != 32 && ivText.matches("^[a-fA-F0-9]+$")) {
            System.out.print("Initialization vector length must be 32 hex-digits. Please try again:\n> ");
            ivText = scanner.next();
        }

        SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), "AES");
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

        try (InputStream is = Files.newInputStream(Path.of(args[1]));
                OutputStream os = Files.newOutputStream(Path.of(args[2]))){
            byte[] buff = new byte[4096];
            int r;
            while(true){
                r = is.read(buff);
                if (r < 4096) break;
                os.write(cipher.update(buff));
            }
            if (r != -1) os.write(cipher.doFinal(buff, 0, r));
        } catch (IOException e) {
            System.out.printf("File \"%s\" not found, or something went wrong with it", args[1]);
        }

        System.out.print(encrypt ? "Encryption" : "Decryption");
        System.out.printf(" completed. Generated file %s based on file %s.\n", args[2], args[1]);


    }
}
