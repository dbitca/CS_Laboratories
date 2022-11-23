package Classical_Ciphers;

public class Vigenere extends SimpleCaesar {
    String generateKey(String str, String key){
        int x = str.length();
        for (int i = 0; ; i++){
            if (x == i)
                i = 0;
            if (key.length() == str.length())
                break;
            key += (key.charAt(i));
        }
        return key;
    }
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
}
