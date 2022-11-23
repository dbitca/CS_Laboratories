package Classical_Ciphers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

    public class PermutationCaesar implements Cipher{
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

        public String permutate (String key){
            String newalphabet = cleanKey(key);

            List<Character> list = new ArrayList<Character>();
            for (int i = 0; i < key.length(); i++) {
                list.add(key.charAt(i));
            }

            for (int i = 0; i < 26; i++) {
                //using only lowercase
                char ch = (char) (i + 97);
                if (!list.contains(ch))
                    newalphabet += ch;
            }

            return newalphabet;
        }

        @Override
        public String encrypt(String text, String key) {
            text = text.toLowerCase();
            String encrypted = "";
            String alphabet = permutate(key);
            int keyint = key.length();

            for (int i = 0; i < text.length(); i++){
                int current = alphabet.indexOf(text.charAt(i));
                int encryptPOS = (keyint + current) % 26;
                char encryptChar = alphabet.charAt(encryptPOS);
                encrypted +=encryptChar;
            }
            return encrypted;
        }

        @Override
        public String decrypt(String text, String key) {
            text = text.toLowerCase();
            String decrypted = "";
            String alphabet = permutate(key);
            int keyint = key.length();
            for (int i =0; i < text.length(); i++){
                int current = alphabet.indexOf(text.charAt(i));
                int decryptPos = (current - keyint) % 26;
                if (decryptPos < 0){
                    decryptPos = alphabet.length() + decryptPos;
                }
                char decryptChar = alphabet.charAt(decryptPos);
                decrypted +=decryptChar;

            }
            return decrypted;
        }
    }
