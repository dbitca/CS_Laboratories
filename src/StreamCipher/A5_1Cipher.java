package StreamCipher;

import Utils.Conversions;
import Utils.Operations;

import java.util.ArrayList;
import java.util.Collections;

import static java.util.Arrays.asList;

public class A5_1Cipher {
    public static ArrayList<Integer> R1 = new ArrayList<Integer>(Collections.nCopies(19, 0));
    public static ArrayList<Integer> R2 = new ArrayList<Integer>(Collections.nCopies(22, 0));
    public static ArrayList<Integer> R3 = new ArrayList<Integer>(Collections.nCopies(23, 0));
    //Set list of feedback bits for each LFSR
    public static ArrayList<Integer> feedbackBitsR1 = new ArrayList<Integer>(asList(13, 16, 17, 18));
    public static ArrayList<Integer> feedbackBitsR2 = new ArrayList<Integer>(asList(20, 21));
    public static ArrayList<Integer> feedbackBitsR3 = new ArrayList<Integer>(asList(20, 21, 22));
    //set clocking bits for each LFSR
    public static int clockingBitR1 = 8;
    public static int clockingBitR2 = 10;
    public static int clockingBitR3 = 10;
    static ArrayList<Integer> keyStream = new ArrayList<>();

    public static String Encrypt(String message) {
        //convert the string message into arrayList of 0's and 1's
        ArrayList<Integer> messageConvertedInBinary = Conversions.ConvertListToIntegers(message);

        //randomly generate the session key
        ArrayList<Integer> sessionkey = Operations.RandomGeneratorOfSignals(64);

        //xor feedbackbits and session key
        Operations.ListOfSignalsAndFeedbackBitsXored(R1, feedbackBitsR1, sessionkey);
        Operations.ListOfSignalsAndFeedbackBitsXored(R2, feedbackBitsR2, sessionkey);
        Operations.ListOfSignalsAndFeedbackBitsXored(R3, feedbackBitsR3, sessionkey);

        //randomly generate frame counter
        ArrayList<Integer> frameCounter = Operations.RandomGeneratorOfSignals(22);

        //xor feedback bits and frame counter
        Operations.ListOfSignalsAndFeedbackBitsXored(R1, feedbackBitsR1, frameCounter);
        Operations.ListOfSignalsAndFeedbackBitsXored(R2, feedbackBitsR2, frameCounter);
        Operations.ListOfSignalsAndFeedbackBitsXored(R3, feedbackBitsR3, frameCounter);

        //compute majority 100 times and clock the registers if needed
        for (int i = 0; i < 100; i++) {
            Integer majority = Operations.calculateMajority();
            Operations.clock(majority, R1, clockingBitR1, feedbackBitsR1);
            Operations.clock(majority, R2, clockingBitR2, feedbackBitsR2);
            Operations.clock(majority, R3, clockingBitR3, feedbackBitsR3);
        }

        //generate key stream
        for (int i = 0; i < 228; i++) {
            Integer generateKeyStream = Operations.LastBitXored();
            keyStream.add(generateKeyStream);
            //value to stock majority
            Integer valueOfMajority = Operations.calculateMajority();
            Operations.clock(valueOfMajority, R1, clockingBitR1, feedbackBitsR1);
            Operations.clock(valueOfMajority, R2, clockingBitR2, feedbackBitsR2);
            Operations.clock(valueOfMajority, R3, clockingBitR3, feedbackBitsR3);
        }
        //XOR binary message and generated key stream
        ArrayList<Integer> encryptedBinaryMessage = new ArrayList<>();
        for (int i = 0; i < messageConvertedInBinary.size(); i++) {
            if (messageConvertedInBinary.get(i) == keyStream.get(i)) encryptedBinaryMessage.add(0);
            else encryptedBinaryMessage.add(1);
        }
        //transform the numbers into letters
        return Conversions.ConvertToCharacters(encryptedBinaryMessage);
    }

    public static String Decrypt(String encryptedMessage) {
        //convert the string message into arrayList of 0's and 1's
        ArrayList<Integer> encryptedBinaryMessage = Conversions.ConvertListToIntegers(encryptedMessage);

        //XOR encrypted binary message and key stream
        ArrayList<Integer> decryptedBinaryMessage = new ArrayList<>();
        for (int i = 0; i < encryptedBinaryMessage.size(); i++) {
            if (encryptedBinaryMessage.get(i) == keyStream.get(i)) decryptedBinaryMessage.add(0);
            else decryptedBinaryMessage.add(1);
        }

        //transform the numbers into letters
        return Conversions.ConvertToCharacters(decryptedBinaryMessage);
    }


}
