# Laboratory 1

### Course: Cryptography & Security
### Author: Bitca Dina

----

## Theory
In cryptography, a classical cipher is a type of cipher that was used historically but for the most part, has fallen into disuse. In contrast to modern cryptographic algorithms, most classical ciphers can be practically computed and solved by hand. However, they are also usually very simple to break with modern technology.
This laboratory work required the implementation of four classical ciphers:

* Caesar Cipher: one of the earliest and simplest methods of text encryption. Using only an integer value as key (K), this method enables plaintext (P) encryption through the following formula:

  E = (P + K) mod 26;

  And for decryption:

  P = (E - K) mod 26;


* Vigenere Cipher: a polyalphabetic cipher, based on multiple substitutions. This method takes as key (K) a text, which is multiplied until it reaches the lenght of the plain text (P). For encryption, the formula applied is:

  E = (P + K) mod 26;

  And for decryption:

  P = (E - K) mod 26;

* Playfair Cipher: this is the first practical diagraph substitution cipher. In this cipher, unlike traditional cipher, we encrypt a pair of alphabets instead of a single alphabet. The algorithm for Playfair is more complex and implies throught key (K) and plaintext (P) prelucration, as well as multiple encryption formulas, depending on the position of the characters in a pair.

* Permutation Caesar Cipher: is a combination between Playfair and Caesar Cipher. The difference between Simple Caesar and Permutation Caesar is that the later will use a text key to permutate the letters in the alphabet before implementing the substitution. Thus, it would also need two keys for the decryption.



## Objectives:

* Getting familiar with the basics of cryptography and classical ciphers.

* Implementing 4 types of the classical ciphers:

    * Caesar cipher with one key used for substitution;
    * Caesar cipher with one key used for substitution, and a permutation of the alphabet;
    * Vigenere cipher;
    * Playfair cipher;

* Structure the project in methods/classes/packages as needed.


## Implementation description

I started by creating an interface called "Cypher" which has two methods: encrypt and decrypt, both using two strings as key.

See code snippet below:

```
public interface Cipher {
    String encrypt (final String text, String key);
    String decrypt (final String text, String key);
}
```
As these methods have been implemented through @override in all of the ciphers, they are the core code snippets of each cipher implementation.

* Caesar Cipher

Since Caesar cipher takes as key an integer, rather than a String, I used "parseInt" to modify the key input. Next, I created an iteration of text length and applied the formula:
E = (P + K) mod 26, for each character in the plaintext. Iteration stops when the "encrypted" String is as long as the "Plaintext" String.
See code snippet below:

```
   @Override
    public String encrypt (String text, String key){
        text = text.toLowerCase();
        String encrypted = "";
        int keyint = Integer.parseInt(key);
        for (int i = 0; i < text.length(); i++){
            int current = alphabet.indexOf(text.charAt(i));
            int encryptPOS = (keyint + current) % 26;
            char encryptChar = alphabet.charAt(encryptPOS);
            encrypted +=encryptChar;
        }
return encrypted;
    }
}
```
The decryption process is the same as the encryption process, except the formula applied and an added condition.

```
@Override
    public String decrypt (String text, String key){
        text.toLowerCase();
        String decrypted = "";
        int keyint = Integer.parseInt(key);
        for (int i =0; i < text.length(); i++){
            int current = alphabet.indexOf(text.charAt(i));
            int decryptPos = (current - keyint) % 26;
            //daca pozitia e mai mica decat 0, luam character de la sfarsitul alfabetului
            if (decryptPos < 0){
                decryptPos = alphabet.length() + decryptPos;
            }
            char decryptChar = alphabet.charAt(decryptPos);
            decrypted +=decryptChar;
        }
        return decrypted;
```

* Permutation Caesar Cipher

The encrypt and decrypt methods for Permutation Caesar Cipher is the same as the one for Simple Caesar, except one added method: permutate. This method includes another method: cleanKey (String key). I used cleanKey to remove duplicate characters from the key, using a LinkedHashSet. That key is then used in permutate (String Key) method to create a permutated alphabet. The core piece of code in permutate (String Key) is displayed below:

```
String newalphabet = cleanKey(key);
   for (int i = 0; i < 26; i++) {
            //using only lowercase
            char ch = (char) (i + 97);
            if (!list.contains(ch))
                newalphabet += ch;
        }
        return newalphabet;
```

* Vigenere Cipher

To implement Vigenere Cipher I created a method called generateKey(String str, String key). This method generates the same key until it reaches the plaintext length. I did it using an infinite loop which breaks when key length reaches string length.
After calling generateKey in the encrypt and decrypt methods, I iterated through plaintext/encrypted text length and applied the Vigenere formula for each character in the text.

See code snippet for encrypt method below:

```
@Override
public String encrypt(String text, String key1){
       String cipher_text ="";
       String key2 = generateKey(text, key1);
       for (int i = 0; i < text.length(); i++){
           int x = (text.charAt(i) + key2.charAt(i)) % 26;
           x += 'A';
           cipher_text += (char)(x);
       }
       return cipher_text;
   }
```

See code snippet for decrypt method below:

```
@Override
   public String decrypt (String text, String key1){
       String orig_text = "";
       String key2 = generateKey(text, key1);
       for (int i = 0; i < text.length() && i < key2.length(); i++){
           int x = (text.charAt(i) - key2.charAt(i) + 26) % 26;
           x += 'A';
           orig_text += (char)(x);
       }
       return orig_text;
   }
```

* Playfair Cipher
  This cipher includes multiple linked methods for plaintext and key prelucration.
  First step is to remove duplicate characters from the key. This has been implemented through a LinkedHashSet of characters.
  See code snippet below:
```
 public String cleanKey (String key) {
        LinkedHashSet<Character> distinctKey = new LinkedHashSet<Character>();
        String newKey = "";
        for (int i = 0; i < key.length(); i++)
        distinctKey.add(key.charAt(i));
        Iterator<Character> it = distinctKey.iterator();
        while (it.hasNext())
        newKey += (Character)it.next();
        key = newKey;
        return key;
    }
```
The output from cleanKey is then passed onto the generateTable function which permutates alphabet according to the key, places all letters in the Platfair Cipher Key Matrix and prints the matrix.
See code snippet below:

```
public void generateTable(String key) {
        key = cleanKey(key);
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == 'j')
                continue;
            list.add(key.charAt(i));
        }
        for (int i = 0; i < 26; i++) {
            //using only lowercase
            char ch = (char) (i + 97);
            if (ch == 'j')
                continue;
            if (!list.contains(ch))
                key += ch;
        }
        //create cipher key table
        for (int i = 0, x = 0; i < 5; i++)
            for (int j = 0; j < 5; j++, x++)
                matrix[i][j] = key.charAt(x);
        System.out.println("Playfair Cipher Key Matrix: ");
        for (int i = 0; i < 5; i++)
            System.out.println(Arrays.toString(matrix[i]));
    }
```
Next, I used the function cleaninput to remove letter "j" from input and replace with "i". Then, I added the letter "x" if duplicate letters are found in the same substring. Last, I add the character "x" to messages of odd length.

See code snippet below:
```
    public String cleaninput(String inputtext){
        
        String message = "";
        int length = inputtext.length();
        for (int i =0; i < length; i++){
            //if input text contains character "j" replace with "i"
            if (inputtext.charAt(i) == 'j')
                message += 'i';
            else
                message += inputtext.charAt(i);
        }
        for (int i =0; i < message.length(); i+=2)
        {
            if (message.charAt(i) == message.charAt(i +1))
                message = message.substring(0, i+1) + 'x' + message.substring(i+1);
        }
        //make the characters of even length
        if (length % 2 ==1)
            message += 'x';
        return message;
    }
```
The last function used is formPairs, which creates substrings of two characters and places them in a String array. Last, but not least, I created a method to get the Character possition and which will return only the position [i] for the first Character and only the position [j] for the second Character.

See code snippet below:
```
  public int[] getCharPos(char ch)
    {
        int[] keyPos = new int[2];
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (matrix[i][j] == ch)
                {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    break;
                }
            }
        }
        return keyPos;
    }
```
I implemented the three cases: in the same line, in the same row, different lines and different rows in the encrypt and decrypt functions.
See code snippet below:
```
  @Override
    public String encrypt(String inputtext, String key) {
        inputtext = inputtext.toLowerCase();
        key = key.toLowerCase();
        String message = cleaninput(inputtext);
        generateTable(key);
        String[] msgPairs = formPairs(message);
        String encText = "";
        for (int i = 0; i < msgPairs.length; i++){
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);
            //if both characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]){
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            }
            //if both characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            }
            //if both characters are in different rows and columns
            else{
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }
            //get the corresponding cipher characters from the key matrix
            encText = encText + matrix[ch1Pos[0]][ch1Pos[1]] + matrix[ch2Pos[0]][ch2Pos[1]];
        }
        return encText;
    }
    @Override
    public String decrypt(String enctext, String key) {
        enctext = enctext.toLowerCase();
        key = key.toLowerCase();
        generateTable(key);
        String[] msgPairs = formPairs(enctext);
        generateTable(key);
        String dectext = "";
        for (int i = 0; i < msgPairs.length; i++){
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);
            //if both characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]){
                if (ch1Pos[1] > 1)
                ch1Pos[0] = (ch1Pos[1] - 1);
                else
                    ch1Pos[0] = 4;
                if (ch2Pos[0] > 1)
                ch2Pos[0] = (ch2Pos[1] - 1);
                else
                    ch2Pos[0] = 4;
            }
            //if both characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                if (ch1Pos[0] > 0)
                ch1Pos[0] = (ch1Pos[0] - 1);
                else
                    ch1Pos[0] = 4;
                if (ch2Pos[0] > 0)
                ch2Pos[0] = (ch2Pos[0] + 1);
                else
                    ch2Pos[0] = 4;
            }
            //if both characters are in different rows and columns
            else{
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }
            //get the corresponding cipher characters from the key matrix
            dectext = dectext + matrix[ch1Pos[0]][ch1Pos[1]] + matrix[ch2Pos[0]][ch2Pos[1]];
        }
        return dectext;
    }
```

This is the main class, in which I tested all of the ciphers, along with their encrypt and decrypt function. I first did the encryption, then used the encrypted messages as input for the decription, in order to check tif both are functioning correctly.

See code snippet below.
```
public class Main {
    public static void main(String[] args) {
        String TextToEncrypt = "magician";
        String TextKey = "alo";
        String IntKey = "5";
        SimpleCaesar simpleCaesar = new SimpleCaesar();
        PermutationCaesar permutationCaesar = new PermutationCaesar();
        Vigenere vigenere = new Vigenere();
        Playfair playfair = new Playfair();
        System.out.println("Text to encrypt: magician");
        System.out.println("Simple Caesar");
        String caesar1 = simpleCaesar.encrypt(TextToEncrypt,IntKey);
        printencrypted(caesar1);
        System.out.println("Permutation Caesar");
        String caesar2 = permutationCaesar.encrypt(TextToEncrypt, TextKey);
        printencrypted(caesar2);
        System.out.println("Vigenere");
        String vigen = vigenere.encrypt("MAGICIAN", "ALO");
        printencrypted(vigen);
        System.out.println("Playfair");
        String play = playfair.encrypt(TextToEncrypt, TextKey);
        printencrypted(play);
        System.out.println("Decrypted messages: ");
        System.out.println("Simple Caesar");
        String deccaesar = simpleCaesar.decrypt("rflnhnfs", IntKey);
        printdecrypted(deccaesar);
        System.out.println("Permutation Caesar");
        String decpermcaesar = permutationCaesar.decrypt("qbjmfmbr", TextKey);
        printdecrypted(decpermcaesar);
        System.out.println("Vigenere");
        String decvigenere = vigenere.decrypt("MLUINWAY", "ALO");
        printdecrypted(decvigenere);
        System.out.println("Playfair");
        String decplayfair = playfair.decrypt("iodnapbi", TextKey);
        printdecrypted(decplayfair);
    }
    public static void printencrypted (String output){
        System.out.println("Encrypted message: " + output);
    }
    public static void printdecrypted (String output){
        System.out.println("Decrypted message: " + output);
    }
}
```

## Conclusions / Screenshots / Results

Since I received the same output through the decryption process as the input in the encryption proces, the conclusion is that these ciphers have been implemented successfully.
Please check the screenshots below

![](2022-09-29-12-34-05.png)
![](2022-09-29-12-33-13.png)