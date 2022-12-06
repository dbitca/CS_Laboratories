package AsymmetricCipher;

import java.util.ArrayList;
import java.util.Random;

public class RSA_Utils {
    public static String bytesToString(byte[] encrypted){
        StringBuilder encrypt = new StringBuilder();
        for (byte b: encrypted){
            encrypt.append(b);
        }
        return encrypt.toString();
    }
}


