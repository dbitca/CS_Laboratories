import HashFunctions.DigitalSignatureCheck;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
      /*  String text = "123456ABCD132536";
        String key = "AABB09182736CCDD";
        DES cipher = new DES();
        System.out.println("Encryption:\n");
        text = cipher.encrypt(text, key);
        System.out.println("\nCipher Text: " + text.toUpperCase() + "\n");
        System.out.println("Decryption\n");
        text = cipher.decrypt(text, key);
        System.out.println("\nPlain Text: "
                + text.toUpperCase());
    }*/
      // Tests.RunRSACipher();
       // Tests.RunSHA();
       DigitalSignatureCheck.signatureCheck();
    }
}