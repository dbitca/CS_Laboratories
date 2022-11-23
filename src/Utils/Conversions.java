package Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Conversions {
    // hexadecimal to binary conversion
    public static String hextoBin(String input) {
        int n = input.length() * 4;
        input = Long.toBinaryString(
                Long.parseUnsignedLong(input, 16));
        while (input.length() < n)
            input = "0" + input;
        return input;
    }

    // binary to hexadecimal conversion
    public static String binToHex(String input) {
        int n = (int) input.length() / 4;
        input = Long.toHexString(
                Long.parseUnsignedLong(input, 2));
        while (input.length() < n)
            input = "0" + input;
        return input;
    }

    public static String ConvertToCharacters(ArrayList<Integer> message) {
        //initialize list to stock binary values
        ArrayList<Integer> binaryLetter = new ArrayList<>();
        String encryptedMessageWithLetters = "";
        //iterate the message to split it into blocks of 8 bits
        for (int i = 0; i < (message.size()); i += 8) {
            binaryLetter.clear();
            for (int j = 0; j < 8; j++) {
                binaryLetter.add(message.get(i + j));
            }
            encryptedMessageWithLetters += (char) ConvertBinaryToDecimal(binaryLetter);

        }
        return encryptedMessageWithLetters;
    }

    public static int ConvertBinaryToDecimal(ArrayList<Integer> message) {
        int asciiCode = 0;
        //iterate the binary list
        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) == 1) {
                int compute = (int) Math.pow(2, (message.size() - (i + 1)));
                asciiCode = asciiCode + compute;
            }
        }
        return asciiCode;
    }

    public static String LettersConverter(String message) {
        String binaryMessage = "";
        for (Character character : message.toCharArray()) {
            var asciiCode = (int) character;
            //if the character is space, ignore it
            if (asciiCode == 32) continue;
            var a = DecimalToBinaryConverter(asciiCode);
            binaryMessage += DecimalToBinaryConverter(asciiCode);
        }
        return binaryMessage;
    }

    public static String DecimalToBinaryConverter(int asciiCode) {
        String binary = "";
        //initialize the value to verify if the binary value of it is of 8 bit length
        int ascii = asciiCode;

        while (asciiCode != 0) {
            int remainder = asciiCode % 2;
            asciiCode = asciiCode / 2;
            binary = binary + remainder;

        }
        //if ascii code is less than 64, that means that the value in binary can be stocked with 6 bits
        //--->thus, two 0's are added
        //else if ascii code is between 64 and 128, it has the value in binary of length 7
        //--->this, one 0 is added
        if (ascii < 64) {
            binary = "00" + InverseString(binary);
        } else if (ascii < 128 && ascii > 64) {
            binary = "0" + InverseString(binary);
        } else binary = InverseString(binary);
        return binary;
    }

        public static String InverseString(String binaryString){
            String binaryInverted = "";
            for (int i = binaryString.length() - 1; i >= 0; i--) {
                binaryInverted = binaryInverted + binaryString.charAt(i);
            }
            return binaryInverted;
        }
    public static ArrayList<Integer> ConvertListToIntegers(String message) {
        String binaryMessage = LettersConverter(message);
        ArrayList<String> myList = new ArrayList<>(Arrays.asList(binaryMessage.split("")));
        //System.out.println(myList);
        ArrayList<Integer> myIntegerList = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++) {
            myIntegerList.add(Integer.valueOf(myList.get(i)));
        }
        //System.out.println(myIntegerList);
        return myIntegerList;
    }
}

