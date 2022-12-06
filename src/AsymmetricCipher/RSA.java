package AsymmetricCipher;

import java.sql.Array;
import java.util.ArrayList;
import java.math.*;
import java.util.Random;

public class RSA {
   private BigInteger p;
    private BigInteger q;
    private static BigInteger N;
    private BigInteger phi;
    private static BigInteger e;
    private static BigInteger d;
   private int bitLength = 1024;
   private Random r;

   public RSA() {
       r = new Random();
       //assigning random prime values of bit length 50
       p = BigInteger.probablePrime(bitLength, r);
       q = BigInteger.probablePrime(bitLength, r);
       System.out.println("Value of p: " + p);
       System.out.println("Value of q: " + q);
       //Calculating p * q
       N = p.multiply(q);
       //Calculating phi (n) using Big Integer math
       //phi = (p -1) * (q -1 )
       phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
       //chose a random nr e such that 1 < e < phi(n)
       e = BigInteger.probablePrime(bitLength / 2, r);
       //comparing gcd (phi, e) with 1 (should be 0) and comparing e to phi (phi needs to be smaller than e)
       while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
           //increasing value of e until condition is satisfied
           e.add(BigInteger.ONE);
       }
       System.out.println("Public key e: " + e);
       //Calculating d such that d * e mod phi = 1;
       d = e.modInverse(phi);
       System.out.println("Private key is: " + d);
   }

    //initiating RSA object
    public RSA (BigInteger e, BigInteger d, BigInteger N){
        RSA.e = e;
        RSA.d = d;
        RSA.N = N;
    }

   public static byte[] encrypt (byte[] message){
       //Applying the formula c = m^e mod n
       return(new BigInteger(message).modPow(e,N).toByteArray());
   }

   public static byte[] decrypt (byte[] encryptedmessage){
       //Applying the formula d = m^c mod N
       return(new BigInteger(encryptedmessage)).modPow(d,N).toByteArray();
   }
   }





