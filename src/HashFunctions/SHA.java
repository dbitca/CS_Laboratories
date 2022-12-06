package HashFunctions;

import AsymmetricCipher.RSA;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class SHA {
    public String HashString(String input) throws NoSuchAlgorithmException {
        //message digest workd with MD2, MD5, HashFunctions.SHA-1, HashFunctions.SHA-224, HashFunctions.SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);
        return bigInt.toString();
    }

    public static void PasswordCheck() throws NoSuchAlgorithmException {
        SHA hash = new SHA();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your password ");
        ArrayList<String> passwords = new ArrayList<>();
        ArrayList<String> hashedpasswords = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String userPassword = scanner.nextLine();
            if (userPassword.equals("done")) break;
            else {
                passwords.add(userPassword);
                String hashedpass = hash.HashString(userPassword);
                if (hashedpasswords.contains(hashedpass) == false)
                    hashedpasswords.add(hashedpass);
                else
                    System.out.println("Password already taken, please enter another password");
                    passwords.remove(userPassword);
            }
        }

    }
}
