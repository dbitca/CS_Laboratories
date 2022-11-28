import AsymmetricCipher.RSA;
import AsymmetricCipher.RSA_Utils;
import BlockCipher.DES;

public class Tests {
    public static void RunBlockCipher(String text, String key){
        DES cipher = new DES();
        System.out.println("Encryption:\n");
        text = cipher.encrypt(text, key);
        System.out.println("\nCipher Text: " + text.toUpperCase() + "\n");
        System.out.println("Decryption\n");
        text = cipher.decrypt(text, key);
        System.out.println("\nPlain Text: "
                + text.toUpperCase());
    }

    public static void RunRSACipher(){
        RSA rsa = new RSA();
        String message = ("hello");
        System.out.println("Encrypting string: " + message);
        System.out.println("String in bytes: "+ RSA_Utils.bytesToString(message.getBytes()));
        byte[] encrypted = rsa.encrypt(message.getBytes());
        byte[] decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypting Bytes : "+ RSA_Utils.bytesToString(decrypted));
        System.out.println("Decrypted string: " + new String(decrypted));
    }
}
