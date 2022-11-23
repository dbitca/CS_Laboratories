package BlockCipher;

import static BlockCipher.Constants.IP;
import static BlockCipher.Constants.IP1;
import static BlockCipher.DES_Operations.*;

public class DES {
    public String encrypt(String message, String key)
    {
        int i;
        // get round keys
        String keys[] = getKeys(key);

        // initial permutation
        message = permutation(IP, message);
        System.out.println("After initial permutation: "
                + message.toUpperCase());
        System.out.println(
                "After splitting: L0="
                        + message.substring(0, 8).toUpperCase()
                        + " R0="
                        + message.substring(8, 16).toUpperCase()
                        + "\n");

        // 16 rounds
        for (i = 0; i < 16; i++) {
            message = round(message, keys[i], i);
        }

        // 32-bit swap
        message = message.substring(8, 16)
                + message.substring(0, 8);

        // final permutation
        message = permutation(IP1, message);
        return message;
    }

    public String decrypt(String plainText, String key)
    {
        int i;
        // get round keys
        String keys[] = getKeys(key);

        // initial permutation
        plainText = permutation(IP, plainText);
        System.out.println("After initial permutation: "
                + plainText.toUpperCase());
        System.out.println(
                "After splitting: L0="
                        + plainText.substring(0, 8).toUpperCase()
                        + " R0="
                        + plainText.substring(8, 16).toUpperCase()
                        + "\n");

        // 16-rounds
        for (i = 15; i > -1; i--) {
            plainText
                    = round(plainText, keys[i], 15 - i);
        }
        // 32-bit swap
        plainText = plainText.substring(8, 16)
                + plainText.substring(0, 8);

        plainText = permutation(IP1, plainText);
        return plainText;
    }
}
