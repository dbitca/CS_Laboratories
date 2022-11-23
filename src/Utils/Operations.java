package Utils;

import StreamCipher.A5_1Cipher;

import java.util.ArrayList;
import java.util.Random;

public class Operations {

    //Perform XOR operation only of feedback bits
    private static Integer XORFeedbackBits(ArrayList<Integer> register, ArrayList<Integer> feedbackBits) {
        Integer n = 0;
        for (int i = 0; i < feedbackBits.size(); i++) {
            //count the number of ones
            if (register.get(feedbackBits.get(i)) == 1)
                n++;
        }
        //perform XOR operation
        if (n % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    //randomly generate 0's and 1's (used for session key and for frame counter)
    public static ArrayList<Integer> RandomGeneratorOfSignals(int number) {
        ArrayList<Integer> SessionKey = new ArrayList<Integer>();
        Random randKeys = new Random();
        for (int i = 0; i < number; i++) {
            int n = randKeys.nextInt(2);
            SessionKey.add(n);
        }
        return SessionKey;
    }

    //shift registers to the right one position.
    private static ArrayList<Integer> ShiftRegister(ArrayList<Integer> register, Integer newValueToAdd) {
        //change the position of each bit, increasing position number with 1
        for (int i = register.size() - 1; i > 0; i--) {
            register.set(i, register.get(i - 1));
        }
        //add new value in the first position
        register.set(0, newValueToAdd);
        return register;
    }

    //XOR operation between feedback bits and the bit from session key / frame counter
    public static ArrayList<Integer> ListOfSignalsAndFeedbackBitsXored(ArrayList<Integer> register, ArrayList<Integer> feedbackBits, ArrayList<Integer> resultBits) {
        Integer value = 0;
        for (int i = 0; i < resultBits.size(); i++) {
            if (XORFeedbackBits(register, feedbackBits) == resultBits.get(i)) {
                value = 0;
            } else {
                value = 1;
            }
            ShiftRegister(register, value);
        }
        return register;
    }

    private static Integer getClockingBit(ArrayList<Integer> reg, Integer clockingBit) {
        return reg.get(clockingBit);
    }

    //compute the majority of registers
    public static Integer calculateMajority() {
        int countZero = 0;
        int countOne = 0;
        //initialize a list to stock value of clocking bits
        ArrayList<Integer> clockingBit = new ArrayList<>();
        clockingBit.add(getClockingBit(A5_1Cipher.R1, A5_1Cipher.clockingBitR1));
        clockingBit.add(getClockingBit(A5_1Cipher.R2, A5_1Cipher.clockingBitR2));
        clockingBit.add(getClockingBit(A5_1Cipher.R3, A5_1Cipher.clockingBitR3));
        //iterate the list to count number of 0's and 1's
        for (int i = 0; i < 3; i++) {
            if (clockingBit.get(i) == 0)
                countZero++;
            else
                countOne++;
        }
        if (countOne > countZero) return 1;
        else return 0;
    }

    //Clock the register if the value of the clocking bit and the value of majority are equal
    //Clock --> means to perform xor operation for feedback bits and to shift the register one position in order to add the new computed value
    public static ArrayList<Integer> clock(Integer valueOfMajority, ArrayList<Integer> reg, Integer clockingBit, ArrayList<Integer> feedbackBits) {

        return reg;
    }

    //Compute XOR value of the last digit from all the registers (for generating key stream)
    public static Integer LastBitXored() {
        int nrOfOnes = 0;
        if (A5_1Cipher.R1.get(18) == 1) nrOfOnes++;
        if (A5_1Cipher.R2.get(21) == 1) nrOfOnes++;
        if (A5_1Cipher.R3.get(22) == 1) nrOfOnes++;
        if (nrOfOnes % 2 == 0) return 0;
        else return 1;
    }
}

