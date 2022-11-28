package AsymmetricCipher;

import java.util.ArrayList;
import java.util.Random;

public class RSA_Utils {
    public static String bytesToString(byte[] encrypted){
        String encrypt = "";
        for (byte b: encrypted){
            encrypt += Byte.toString(b);
        }
        return encrypt;
    }
}


