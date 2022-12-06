package BlockCipher;
import static BlockCipher.Constants.*;
import static BlockCipher.DES_Conversions.binToHex;
import static BlockCipher.DES_Conversions.hextoBin;

public class DES_Operations {
    // function to perform permutations according to permutation table
    // per-mutate input hexadecimal
    // according to specified sequence
   public static String permutation(int[] sequence, String input)
    {
        StringBuilder output = new StringBuilder();
        input = hextoBin(input);
        for (int j : sequence) output.append(input.charAt(j - 1));
        output = new StringBuilder(binToHex(output.toString()));
        return output.toString();
    }

    // xor 2 hexadecimal strings
   public static String xor(String a, String b)
    {
        // hexadecimal to decimal(base 10)
        long t_a = Long.parseUnsignedLong(a, 16);
        // hexadecimal to decimal(base 10)
        long t_b = Long.parseUnsignedLong(b, 16);
        // xor
        t_a = t_a ^ t_b;
        // decimal to hexadecimal
        // prepend 0's to maintain length
        StringBuilder aBuilder = new StringBuilder(Long.toHexString(t_a));
        while (aBuilder.length() < b.length())
            aBuilder.insert(0, "0");
        a = aBuilder.toString();
        return a;
    }

    // left Circular Shifting bits
   public static String leftCircularShift(String input, int numBits)
    {
        int n = input.length() * 4;
        int[] perm = new int[n];
        for (int i = 0; i < n - 1; i++)
            perm[i] = (i + 2);
        perm[n - 1] = 1;
        while (numBits-- > 0)
            input = permutation(perm, input);
        return input;
    }

    // preparing 16 keys for 16 rounds
   public static String[] getKeys(String key)
    {
        String[] keys = new String[16];
        // first key permutation
        key = permutation(PC1, key);
        for (int i = 0; i < 16; i++) {
            key = leftCircularShift(key.substring(0, 7),
                    shiftBits[i])
                    + leftCircularShift(
                    key.substring(7, 14),
                    shiftBits[i]);
            // second key permutation
            keys[i] = permutation(PC2, key);
        }
        return keys;
    }

    // s-box lookup
   public static String sBox(String input)
    {
        StringBuilder output = new StringBuilder();
        input = hextoBin(input);
        for (int i = 0; i < 48; i += 6) {
            String temp = input.substring(i, i + 6);
            int num = i / 6;
            int row = Integer.parseInt(
                    temp.charAt(0) + "" + temp.charAt(5),
                    2);
            int col = Integer.parseInt(
                    temp.substring(1, 5), 2);
            output.append(Integer.toHexString(
                    sbox[num][row][col]));
        }
        return output.toString();
    }

  public static String round(String input, String key, int num)
    {
        // fk
        String left = input.substring(0, 8);
        String temp = input.substring(8, 16);
        String right = temp;
        // Expansion permutation
        temp = permutation(EP, temp);
        // xor temp and round key
        temp = xor(temp, key);
        // lookup in s-box table
        temp = sBox(temp);
        // Straight D-box
        temp = permutation(P, temp);
        // xor
        left = xor(left, temp);
        System.out.println("Round " + (num + 1) + " "
                + right.toUpperCase() + " "
                + left.toUpperCase() + " "
                + key.toUpperCase());

        // swapper
        return right + left;
    }

}
