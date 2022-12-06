package StreamCipher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import static java.util.Arrays.asList;

public class A5_1Cipher {
    public static ArrayList<Integer> R1 = new ArrayList<>(Collections.nCopies(19, 0));
    public static ArrayList<Integer> R2 = new ArrayList<>(Collections.nCopies(22, 0));
    public static ArrayList<Integer> R3 = new ArrayList<>(Collections.nCopies(23, 0));
    //Set list of feedback bits for each LFSR
    public static ArrayList<Integer> feedbackBitsR1 = new ArrayList<>(asList(13, 16, 17, 18));
    public static ArrayList<Integer> feedbackBitsR2 = new ArrayList<>(asList(20, 21));
    public static ArrayList<Integer> feedbackBitsR3 = new ArrayList<>(asList(20, 21, 22));
    //set clocking bits for each LFSR
    public static int clockingBitR1 = 8;
    public static int clockingBitR2 = 10;
    public static int clockingBitR3 = 10;
    static ArrayList<Integer> keyStream = new ArrayList<>();

    public static String Encrypt(String message) {
        //convert the string message into arrayList of 0's and 1's
        ArrayList<Integer> messageConvertedInBinary = A5_Conversions.ConvertListToIntegers(message);

        //randomly generate the session key
        ArrayList<Integer> sessionkey = A5_Operations.RandomGeneratorOfSignals(64);

        //xor feedbackbits and session key
        A5_Operations.ListOfSignalsAndFeedbackBitsXored(R1, feedbackBitsR1, sessionkey);
        A5_Operations.ListOfSignalsAndFeedbackBitsXored(R2, feedbackBitsR2, sessionkey);
        A5_Operations.ListOfSignalsAndFeedbackBitsXored(R3, feedbackBitsR3, sessionkey);

        //randomly generate frame counter
        ArrayList<Integer> frameCounter = A5_Operations.RandomGeneratorOfSignals(22);

        //xor feedback bits and frame counter
        A5_Operations.ListOfSignalsAndFeedbackBitsXored(R1, feedbackBitsR1, frameCounter);
        A5_Operations.ListOfSignalsAndFeedbackBitsXored(R2, feedbackBitsR2, frameCounter);
        A5_Operations.ListOfSignalsAndFeedbackBitsXored(R3, feedbackBitsR3, frameCounter);

        //compute majority 100 times and clock the registers if needed
        for (int i = 0; i < 100; i++) {
            Integer majority = A5_Operations.calculateMajority();
            A5_Operations.clock(majority, R1, clockingBitR1, feedbackBitsR1);
            A5_Operations.clock(majority, R2, clockingBitR2, feedbackBitsR2);
            A5_Operations.clock(majority, R3, clockingBitR3, feedbackBitsR3);
        }

        //generate key stream
        for (int i = 0; i < 228; i++) {
            Integer generateKeyStream = A5_Operations.LastBitXored();
            keyStream.add(generateKeyStream);
            //value to stock majority
            Integer valueOfMajority = A5_Operations.calculateMajority();
            A5_Operations.clock(valueOfMajority, R1, clockingBitR1, feedbackBitsR1);
            A5_Operations.clock(valueOfMajority, R2, clockingBitR2, feedbackBitsR2);
            A5_Operations.clock(valueOfMajority, R3, clockingBitR3, feedbackBitsR3);
        }
        //XOR binary message and generated key stream
        ArrayList<Integer> encryptedBinaryMessage = new ArrayList<>();
        for (int i = 0; i < messageConvertedInBinary.size(); i++) {
            if (Objects.equals(messageConvertedInBinary.get(i), keyStream.get(i))) encryptedBinaryMessage.add(0);
            else encryptedBinaryMessage.add(1);
        }
        //transform the numbers into letters
        return A5_Conversions.ConvertToCharacters(encryptedBinaryMessage);
    }

    public static String Decrypt(String encryptedMessage) {
        //convert the string message into arrayList of 0's and 1's
        ArrayList<Integer> encryptedBinaryMessage = A5_Conversions.ConvertListToIntegers(encryptedMessage);

        //XOR encrypted binary message and key stream
        ArrayList<Integer> decryptedBinaryMessage = new ArrayList<>();
        for (int i = 0; i < encryptedBinaryMessage.size(); i++) {
            if (Objects.equals(encryptedBinaryMessage.get(i), keyStream.get(i))) decryptedBinaryMessage.add(0);
            else decryptedBinaryMessage.add(1);
        }

        //transform the numbers into letters
        return A5_Conversions.ConvertToCharacters(decryptedBinaryMessage);
    }


}
