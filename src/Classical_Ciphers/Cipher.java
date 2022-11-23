package Classical_Ciphers;

public interface Cipher {
    String encrypt (final String text, String key);
    String decrypt (final String text, String key);
}

