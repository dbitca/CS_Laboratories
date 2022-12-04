package HashFunctions;

import AsymmetricCipher.RSA;
import AsymmetricCipher.RSA_Utils;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DigitalSignatureCheck {
    public static void signatureCheck() throws NoSuchAlgorithmException {
        RSA rsa = new RSA();
        SHA sha = new SHA();
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        String HashedMessage = sha.HashString(message);
        System.out.println("Encrypting string: " + HashedMessage);
        System.out.println("String in bytes: " + RSA_Utils.bytesToString(HashedMessage.getBytes()));
        byte[] encrypted = rsa.encrypt(HashedMessage.getBytes());
        byte[] decrypted = rsa.decrypt(encrypted);
        if (new String(decrypted).equals(HashedMessage)){
            System.out.println("Signature check successful");
        }
        else System.out.println("Digital signature failed");
    }
}
