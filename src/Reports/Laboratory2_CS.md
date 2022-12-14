# Symmetric Ciphers. Stream Ciphers. Block Ciphers.

### Course: Cryptography & Security
### Author: Bitca Dina

----

## Theory
In this laboratory work we had to implement one stream cipher and one block cipher. While they are both Symmetric Encryption Algorithms, there are some key differences between the two to be pointed out.
Block Ciphers encrypt data in blocks of set length, while Stream Ciphers encrypt plaintext one byte at a time. These two encryptions are quite different in implementation and use cases. To understand these differences, a small explanation for both of these ciphers is due.
* Block Ciphers - convert data in plaintext into ciphertext in fixed-size blocks. The block size generally depends on the encryption scheme and is usually in octaves (64-bit or 128-bit blocks). If the plaintext length is not a multiple of 8, the encryption scheme uses padding to ensure complete blocks. The advantage of this type of encription is "high diffusion", which means that, because the plaintext is objected to multiple encrypt iterations, it is difficult for malicious actors to insert symbols into a data block without detection.On the other hand, block ciphers have a high error propagation rate since a bit of change in the original plaintext results in entirely different ciphertext blocks.

* Stream ciphers - encrypt a continuous string of binary digits by applying time-varying transformations on plaintext data. Therefore, this type of encryption works bit-by-bit, using keystreams to generate ciphertext for arbitrary lengths of plain text messages.
  Encryption schemes that use stream ciphers are less likely to propagate system-wide errors since an error in the translation of one bit does not typically affect the entire plaintext block. Stream encryption also occurs in a linear, continuous manner, making it simpler and faster to implement. On the other hand, stream ciphers lack diffusion since each plaintext digit is mapped to one ciphertext output.

There are three key differences between Stream Ciphers and Block Ciphers to be mentioned:
* Bit conversion:

Block ciphers transform plaintext 1 block (64/128/256 bits) at a time, while stream ciphers convert plaintext to ciphertext 1 byte at a time. This makes block ciphers slower since an entire block has to be accumulated before the data is encrypted/decrypted.

* Translation principle:

Stream ciphers utilize only the confusion principle to transform data, ensuring data confidentiality. On the other hand, block ciphers use data diffusion and confusion to encrypt plaintext. Block ciphers can, therefore, be used to implement authenticated encryption for enhanced security.

* Reversibility:

Stream ciphers use an XOR operation on the plaintext to create ciphertext. Stream-based encryption is easily reversed by XORing the ciphertext outputs. Block ciphers encrypt more bits at a time, making the decryption comparatively complex.


## Objectives:

* Getting familiar with the symmetric cryptography, stream and block ciphers.

* Implementing an example of a stream cipher.

* Implementing an example of a block cipher.


## Implementation description

* Block Cipher Implementation

The type of Block Cipher chosen by me is DES - Data Encryption Standard. The DES (Data Encryption Standard) algorithm is a symmetric-key block cipher, which means that it employs the same key in both encrypting and decrypting the data.  The algorithm takes the plain text in 64-bit blocks and converts them into ciphertext using 48-bit keys.

These are the major steps toward implementing the DES algorithm:

The plaintext message is split into 64 bits blocks. For simplicity, I used a plaintext of exactly 16 letters, which is then transformed into exactly 64 bits. The key is also a 64-bit text.

* Step 1: Create 16 subkeys

  In this step the key is permutated twice. First time using the initial permutation table (PC1) provided as a constant in the code. Afterwards the key is split in 2 equal substrings and shifted right according to the "Shiftbits" table. The shifting is complete after 16 iterations with different shifting value for each round. Last, the key is permutated once again, using  the PC2 table which is also a constant variable. The results are stored in a String Array.

The snipped code for subkey creation is provided below:
```

 key = permutation(PC1, key);
       
        for (int i = 0; i < 16; i++) {
            key = CircularShift(key.substring(0, 7), shiftBits[i]) + CircularShift(key.substring(7, 14), shiftBits[i]);
            keys[i] = permutation(PC2, key);
}
```
* Step 2: Perform initial permutation on plaintext

Performing permutation on plaintext using the initial permutation table (IP) which is a constant.
The permutation function is provided in the code below:
```
for (int i = 0; i < permutationtable.length; i++)
            resultbytes += permbytes.charAt(permutationtable[i] - 1);
        resultbytes = Conversions.binToHex(resultbytes);
```        
The code snippet for plaintext permutation is provided below:

```
plainText = permutation(IP, plainText);
```

* Step 3: Divide permutated plaintext (Ln and Rn) and apply the encryption formula

We now proceed through 16 iterations, for 1<=n<=16, using a function which operates on two blocks--a data block of 32 bits and a key Kn of 48 bits--to produce a block of 32 bits.

Then for n going from 1 to 16 we calculate

Ln = Rn-1

Rn = Ln-1 xor f(Rn-1,Kn)

This results in a final block, for n = 16, of L16R16. That is, in each iteration, we take the right 32 bits of the previous result and make them the left 32 bits of the current step. For the right 32 bits in the current step, we XOR the left 32 bits of the previous step with the calculation function.

To calculate the function f I first expanded each block Rn-1 from 32 bits to 48 bits. This is done by using a selection table that repeats some of the bits in Rn-1

The code snippet for this step is provided below:
```
String left = input.substring(0, 8);
        String temp = input.substring(8, 16);
        String right = temp;
        temp = permutation(EP, temp);
```
Next, in the f calculation, I XORed the output of this function with the Key Kn.

The code snippet for this step is provided below:

```
 public static String xor(String exp_right, String key)
    {
        long first = Long.parseUnsignedLong(exp_right, 16);
        long second = Long.parseUnsignedLong(key, 16);
        first = first ^ second;
        exp_right = Long.toHexString(first);
        while (exp_right.length() < key.length())
            exp_right = "0" + exp_right;
        return exp_right;
    }
```
I now have to work with 8 groups of 6 bits (or 48 bits). Subsequently, I used them as addresses in tables called "S boxes". Each group of six bits will give us an address in a different S box. The explanation for S-box step is quite vast so I will be providing the code snippet only.

```
for (int i = 0; i < 48; i += 6) {
            String temp = input_bits.substring(i, i + 6);
            int num = i / 6;
            int row = Integer.parseInt(
                    temp.charAt(0) + "" + temp.charAt(5),
                    2);
            int col = Integer.parseInt(
                    temp.substring(1, 5), 2);
            S_value += Integer.toHexString(
                    sbox[num][row][col]);
```
After the s-box lookup function I get a 36-bit output, which is the result of the function f.
Finally, I can apply the formula:

Ln = Rn-1

Rn = Ln-1 xor f(Rn-1,Kn)

The code snippet for the formula application is provided below:
```
right_substring = xor(left_substring, temp_right);
return right_substring + left_substring;
```
The final step for DES encryption is applying the final permutation on the 16-th output resulted in the code below, using the IP^-1 table.

The code snippet for this step is provided below:
```
 message = permutation(IP1, message);
 return message;
```
The final result is converted to hexadecimal in the "permutation" function.

Decryption is simply the inverse of encryption, follwing the same steps as above, but reversing the order in which the subkeys are applied.

* Stream Cipher Implementation

For my Stream Cipher Implementation, I chose the A5/1 stream cipher. A5/1 is built from three short linear feedback shift 
registers (LFSR) of lengths 19, 22, and 23 bits. 
The rightmost bit in each register is labelled as bit zero. The taps of Register 1 are at bit positions 13,16,17,18; the taps of Register 2 are at bit positions 20,21; 
and the taps of Register 3 are at bit positions 7, 20,21,22.
When a register is clocked, its taps are XORed together, and the result is stored
in the rightmost bit of the left-shifted register.
The registers are clocked in a stop/go fashion using the following majority rule:
Each register has a single "clocking" tap (bit 8 for R1, bit 10 for R2, and bit 10 for for R3); 
each clock cycle, the majority function of the clocking taps is calculated and only those registers whose clocking taps agree with the majority 
bit are actually clocked. Note that at each step either two or three registers are clocked, and that each register moves with probability 3/4 and stops with probability 1/4.

The algorithm described above is implemented by following the steps below.

* Step 1: Creating three Linear-Feedback Shift Registers of the sizes mentioned above and setting their values to 0:

For the sake of simplicity, I used ArrayLists to store the Registers and used the "Collections.nCopies" function to return
an immutable list which contains n copies of a given object
which, in our case, is 0.
```
public static ArrayList<Integer> R1 = new ArrayList<Integer>(Collections.nCopies(19, 0));
```
* Step 2: Generating Session Key

The session key initially has 64 bits and it should contain
randomly generated values of 1`s and 0`s. 

The code snippet for this step is provided below:

````
  ArrayList<Integer> SessionKey = new ArrayList<Integer>();
        Random randKeys = new Random();
        for (int i = 0; i < number; i++) {
            int n = randKeys.nextInt(2);
            SessionKey.add(n);
        }
````

Step 3: Perform XOR operation between Feedback Bits of each register and the bits in the Session Key:

The same operation is repeated for each of the three registers, the result being stored in the leftmost
position of each register. This function requires 64 iterations, as the Session Key comprises 64 bits.
It also requires the output of the XORed Feedback Bits in order to function so it comprises 2 substeps.

Substep 1: Performing XOR between the feedback Bits of each register.
All of the values of the Feedback Bits for one register are stored in an ArrayList with the
purpose of easily calculating XOR value.
The code snippet for this substep is provided below:
````
 for (int i = 0; i < feedbackBits.size(); i++) {
            if (register.get(feedbackBits.get(i)) == 1)
                n++;
        }
        if (n % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
````
Substep 2: Performing XOR between the result of the first substep and the value in the Session Key.
The code snippet for this substep is provided below:
````
if (XORFeedbackBits(register, feedbackBits) == resultBits.get(i)) {
                value = 0;
            } else {
                value = 1;
            }
````
As a last step, for each iteration we add the value in the working register
and shift all values with one position to the right.
````
for (int i = register.size() - 1; i > 0; i--) {
            register.set(i, register.get(i - 1));
        }
        register.set(0, newValueToAdd);
````

Step 4: Generate a frame counter.

The frame counter consists of 22 bits and is generated randomly following 
the exact same logic as the one used for Session Key generation.

Step 5: Repeat step 3 for the frame counter

This step follows the exact same logic as the one used for computing the XORed values
between the Session Key and the feedback bits of each register.

Step 6: Clocking the registers

As it has been mentioned above, the registers are clocked in a stop/go fashion
by using the majority rule. The first substep to complete in this case
is computing the majority between the clocking bits of each register.

The code snippet for this substep is provided below:
````
for (int i = 0; i < 3; i++) {
            if (clockingBit.get(i) == 0) 
                countZero++;
            else 
                countOne++;
        }
        if (countOne > countZero) return 1;
        else return 0;
````
The output is then compared to the clocking bit of each register. 
If the clocking bit and the output are equal, the register is clocked,
which means that it is open to perform the XOR operation between the feedback bits. 
There are 100 iterations of this step, in each of them the majority is calculated
and then compared to the clocking bits of our registers.

The code snippet for this step is provided below:
````
 for (int i = 0; i < 100; i++) {
            Integer majority = Operations.calculateMajority();
            Operations.clock(majority, R1, clockingBitR1, feedbackBitsR1);
            Operations.clock(majority, R2, clockingBitR2, feedbackBitsR2);
            Operations.clock(majority, R3, clockingBitR3, feedbackBitsR3);
````

Step 7: Generate the key stream

To generate the key stream I repeated step 6 through 228 iterations, but at each iteration 
the last bits of the clocked registers are XORed and added
to the newly formed key stream. This is the last step in the key generation problem.

Step 8: Convert the plaintext into binary

Each letter needs to be converted in
batches of 8 bits, which are then inverted. That is, for each letter, two operations are performed:
convert letter to bits and invert the bits.

Step 9: Performing XOR operation between the binary message and the key stream

This steps represents the encryption process. The newly formed ArrrayList of 228 bits is our encrypted message. As a definitive last
step the binary message is converted back into letters.


## Conclusions / Screenshots / Results
