package BlockCipher;

import Utils.Conversions;

import static BlockCipher.Constants.*;
import static Utils.Conversions.hextoBin;

public class DES_Operations {

    // function to perform permutations according to permutation table
    public static String permutation(int[] permutationtable, String permbytes)
    {
        String resultbytes = "";
        permbytes = hextoBin(permbytes);
        for (int i = 0; i < permutationtable.length; i++)
            resultbytes += permbytes.charAt(permutationtable[i] - 1);
        resultbytes = Conversions.binToHex(resultbytes);
        return resultbytes;
    }
//Step 2: Generating 16 keys
//left circular shift bits for key: Step 2
public static String CircularShift(String input, int numBits)
{
    int n = input.length() * 4;
    int perm[] = new int[n];
    for (int i = 0; i < n - 1; i++)
        perm[i] = (i + 2);
    perm[n - 1] = 1;
    while (numBits-- > 0)
        input = permutation(perm, input);
    return input;
}
    // preparing 16 keys for 16 rounds : Step 2
    public static String[] getKeys(String key)
    {
        String keys[] = new String[16];
        // first key permutation using PC1
        key = permutation(PC1, key);
        //after permutation concatenate keys
        for (int i = 0; i < 16; i++) {
            key = CircularShift(key.substring(0, 7), shiftBits[i]) + CircularShift(key.substring(7, 14), shiftBits[i]);
            // perform permutation again using PC-2
            keys[i] = permutation(PC2, key);
        }
        return keys;
    }
    //perform xor on 2 hexadecimal strings
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


    // s-box lookup : Step 3
   public static String sBox(String input_bits)
    {
        String S_value = "";
        input_bits = hextoBin(input_bits);
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
        }
        return S_value;
    }
   public static String round(String input, String key, int num)
    {
        // split subkey
        String left_substring = input.substring(0, 8);
        String temp_right = input.substring(8, 16);
        String right_substring = temp_right;
        // Expansion permutation
        temp_right = permutation(EP, temp_right);
        // xor temp and round key
        temp_right = xor(temp_right, key);
        // lookup in s-box table
        temp_right = sBox(temp_right);
        // Straight D-box
        temp_right = permutation(P, temp_right);
        // xor
        left_substring = xor(left_substring, temp_right);
        System.out.println("Round " + (num + 1) + " "
                + right_substring.toUpperCase() + " "
                + left_substring.toUpperCase() + " "
                + key.toUpperCase());

        // swapper
        return right_substring + left_substring;
    }

}
