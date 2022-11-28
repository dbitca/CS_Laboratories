# Asymmetric Ciphers

### Course: Cryptography & Security
### Author: Bitca Dina

----

## Theory
Asymmetric ciphers are also referred to as ciphers with public and private keys. They use two keys, one for encryption of messages and the other one during decryption.
The public key is widely known and everybody can use it to encrypt any messages. The idea of asymmetric encryption is that only the owner of the second key (the private key, which is not known to anybody else), can decrypt the message. Similarly, data encrypted with the private key can only be decrypted with the corresponding public key.
symmetric ciphers are much slower than symmetric ciphers (usually thousand times slower). It is common practice to use public key encryption only to establish the secure connection and negotiate the new secret key, which is then used to protect further communication by using symmetric encryption.

## Objectives:

* Get familiar with the asymmetric cryptography mechanisms.
* Implement an example of an asymmetric cipher.
* Use client class or test classes to show execution of program

## Implementation description

* RSA algorithm implementation

The RSA algorithm allows to create a pair of keys: a public key and a private key. Everyone can receive the public key and use it to encrypt a message. Only the owner of the private key would be able to decrypt that message.
Similarly, the owner of the private key can encrypt some data by using it, thus allowing everyone else to use the corresponding public key to decrypt the data.

The critical step for implementing the RSA algorithm is Key Generation. Both RSA keys (public and private) are generated
by following the steps below.
* Public/Private key computation

1. Randomly generate two prime numbers:
In real life application of this algorithm, the prime numbers need to be very lengthy. 
To simulate a real life situation, BigInteger variables throughout the implementation, but their bit length
has been set to 50 bits so checking the result would be comfortable. 
For prime number computation, the predefined "probablePrime(bitLenght, rnd)" method, has been used. The parameter "rnd" is a source of
random bits used to select candidates to be tested for primality.
See code snippet below:
````
 r = new Random();
 p = BigInteger.probablePrime(bitLength, r);
 q = BigInteger.probablePrime(bitLength, r);
````
2. Calculate n = p * q:
The number n is used as the modulus for both private and public keys.
Its length is the length of the RSA key.
See code snippet below:
````
N = p.multiply(q);
````
3. Calculate Euler's totient function for n:
The formula for Euler's totient function is: 
<center><ins>phi(n) = (p - 1) * (q - 1)</ins></center>
See code snippet below:

````
phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
````
4. Randomly Choose an integer e that is larger than 1 and smaller than phi(n):
This step is computed using the "probablePrime (bitlength, rnd)" method. To make sure that
the randomly generated prime is smaller than phi, the bitlength argument has been set to half of the
predefined bit length.
See code snippet below:
````
   e = BigInteger.probablePrime(bitLength / 2, r);
````
5. Check the condition for the numbers e and phi(n) to be coprime.
To do that, a while iteration is used, in which the Greatest Common Divisor between
phi and e is compared to 1. For each iteration, the value of 1 is added to e. The iteration
breaks when the comparison between gcd(phi,e) and 1 equals to 0.
See code snippet below:
````
while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
           e.add(BigInteger.ONE);
       }
````
6. Compute a number d such that:
<center><ins>d * e = 1 mod phi(n)</ins></center>
The variable "d" is the private key exponent. 
See code snippet below:

````
d = e.modInverse(phi);
````

Following the computations stated above, the value for the private key is <e, n> and the value of the public key is <d, n>.

* Encryption

During encryption one should use a public key (n, e). All messages should be divided into a number of parts. Then, each part should be converted to a number (that must be larger than 0 and smaller than n). In practice, the message should be divided into fragments of the size of a certain number of bits.
In this implementation, the length of the key is small so the division has not been implemented.
The decryption is implemented following the formula:
<center><ins>c = m^e (mod n) </ins></center>
See code snippet below:

````
public static byte[] encrypt (byte[] message){
       return(new BigInteger(message).modPow(e,N).toByteArray());
````

* Decryption

During decryption one should use a private key (n, d).

The received ciphertext consists of numbers, which are smaller than n. Each ciphertext number ought to be raised modulo n to the power d.
The decryption is implemented following the formula:
<center><ins>m = c^d (mod n)</ins></center>
See code snippet below:

````
public static byte[] decrypt (byte[] encryptedmessage){
       return(new BigInteger(encryptedmessage)).modPow(d,N).toByteArray();
   }
````

## Conclusions 

For me, the RSA Algorithm implementation has been a good opportunity to practice BigInteger computations.
It has provided valuable information and initiation into the real programming experience as the volume of data
being processed is bigger than the one used in previous algorithm implementations. 
Overall, it has provided new and interesting information.

