package BlockCipher;

import static java.lang.Long.*;

public class DES_Conversions {
  public static String hextoBin(String input)
    {
        int n = input.length() * 4;
        StringBuilder inputBuilder = new StringBuilder(toBinaryString(
                parseUnsignedLong(input, 16)));
        while (inputBuilder.length() < n)
            inputBuilder.insert(0, "0");
        input = inputBuilder.toString();
        return input;
    }

    // binary to hexadecimal conversion
    public static String binToHex(String input)
    {
            int n = input.length() / 4;
        StringBuilder inputBuilder = new StringBuilder(toHexString(
                parseUnsignedLong(input, 2)));
        while (inputBuilder.length() < n)
                inputBuilder.insert(0, "0");
        input = inputBuilder.toString();
        return input;
        }
}
