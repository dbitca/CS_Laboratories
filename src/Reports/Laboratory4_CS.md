# Hash functions and Digital Signatures

### Course: Cryptography & Security
### Author: Bitca Dina

----

## Theory
Hash functions are extremely useful and appear in almost all information security applications.
The term hash function refers to a function that converts a given numerical value to another compressed one. The output of this function is always fixed.
The main component of a hashing algorithm is a function that operates on two sets of fixed-size blocks of data.
The size of each data block varies depending on the algorithm. Typically, the block sizes are from 128 bits to 512 bits.
There is a good amount of Hash Functions available at the moment. Some of the most popular Hash Functions are:
 1. Message Digest (MD)

MD is a 128-bit Hash Function. In the software world, MD5 digests have been widely used to ensure that the files that are transferred are secure. For instance, when a user downloads a file, the file servers will provide a pre-computed checksum for the data.
However, it is not a recommended for use nowadays due to some successful collision attacks in 2004.

 2. Secure Hash Function (SHA)

The SHA group comprises four different algorithms: SHA-0, SHA-1, SHA-2 and SHA-3. The variant used in the scope
of this laboratory work is SHA-256 (a variant of SHA-2), with 256 bits in its Hash value. 
There are no successful attacks reported on SHA-2 hash function.

 3. Whirpool

Whirpool is a 512-bit hash function. It was derived from the modified version of Advanced Encryption Standard.
There are three versions of Whirpool that have been released; namely WHIRPOOL-0, WHIRPOOL-T and WHIRPOOL.

There are two direct applications to Hash Functions:
* Provide protection to password storage
* Check data integrity

More about the implementation details and code application of these functions in the Implementation Description.

## Objectives:

* Get familiar with the hashing techniques/algorithms.
* Use an appropriate hashing algorithms to store passwords in a local DB.
* Use an asymmetric cipher to implement a digital signature process for a user message.


## Implementation description

The Hashing algorithm implemented in the context of this laboratory work 
is SHA (Secure Hash Algorithm), namely SHA-256 algorithm. The SHA-256 algorithm
generates an almost unique, fixed-size 256-bit hash. This is a one-way function, so the result cannot
be decrypted back to the original value.

Java provides inbuilt MessageDigest class for SHA-256 hashing:
One important notion about MessageDigest is that it is not thread safe. For each thread, another instance of 
MessageDigest needs to be created for each thread. However, this particular implementation
does not use threads.

See code snippet below:
````
 MessageDigest md = MessageDigest.getInstance("HashFunctions.SHA-256");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);
````
This algorithm has been used to store passwords in a local database. In this instance, the database chosen 
is a simple String Array. To perform a check on the unique output propriety of the SHA algorithm, the user input is stored 
into a String array. Then, a check is performed to see if the same value of the hashed string has already been added
in the array, which would trigger a "Pasword is not available response", meaning that the exact same hash value is already present
in the database.

See code snippet below:

````
 while (scanner.hasNextLine()) {
            String userPassword = scanner.nextLine();
            if (userPassword.equals("done")) break;
            else {
                passwords.add(userPassword);
                String hashedpass = hash.HashString(userPassword);
                if (hashedpasswords.contains(hashedpass) == false)
                    hashedpasswords.add(hashedpass);
                else
                    System.out.println("Password already taken, plese enter another password");
                    passwords.remove(userPassword);
````
Finally, the Digital Signature check is performed by reading the plaintext input of the user, hashing it and encrypting 
using the RSA encryption formula. The decrypted value is then compared to the hash value of the input. If these values
are equal, this means that the signature is successful. Otherwise, the check has failed, meaning that there is an error within
the encryption process.

See code snippet below:

````
String message = scanner.nextLine();
        String HashedMessage = sha.HashString(message);
        byte[] encrypted = rsa.encrypt(HashedMessage.getBytes());
        byte[] decrypted = rsa.decrypt(encrypted);
        if (new String(decrypted).equals(HashedMessage)){
            System.out.println("Signature check successful");
        }
        else System.out.println("Digital signature failed");
    }
````

## Conclusions 
This laboratory work provided important information on the overall complexity of storing and processing vulnerable input data.
It shed light on the measures taken to protect personal information. The SHA algorithm particularly is proven to be a very
thorough hashing algorithm and a very complex one. 
