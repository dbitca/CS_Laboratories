package Classical_Ciphers;

public class SimpleCaesar implements Cipher {
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
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
    }
}