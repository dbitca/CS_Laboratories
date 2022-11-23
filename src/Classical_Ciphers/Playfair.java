package Classical_Ciphers;

import java.lang.reflect.Array;
import java.util.*;

public class Playfair implements Cipher{

    char[][] matrix = new char[5][5];
    //function to remove duplicates from key
    public String cleanKey (String key) {
        LinkedHashSet<Character> distinctKey = new LinkedHashSet<Character>();
        String newKey = "";
        for (int i = 0; i < key.length(); i++)
            distinctKey.add(key.charAt(i));
        Iterator<Character> it = distinctKey.iterator();
        while (it.hasNext())
            newKey += (Character)it.next();
        key = newKey;
        return key;
    }

    public void generateTable(String key) {
        key = cleanKey(key);
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == 'j')
                continue;
            list.add(key.charAt(i));
        }
        for (int i = 0; i < 26; i++) {
            //using only lowercase
            char ch = (char) (i + 97);
            if (ch == 'j')
                continue;
            if (!list.contains(ch))
                key += ch;
        }
        //create cipher key table
        for (int i = 0, x = 0; i < 5; i++)
            for (int j = 0; j < 5; j++, x++)
                matrix[i][j] = key.charAt(x);

        System.out.println("Playfair Cipher Key Matrix: ");
        for (int i = 0; i < 5; i++)
            System.out.println(Arrays.toString(matrix[i]));
    }
    public String cleaninput(String inputtext){

        String message = "";
        int length = inputtext.length();

        for (int i =0; i < length; i++){
            //if input text contains character "j" replace with "i"
            if (inputtext.charAt(i) == 'j')
                message += 'i';
            else
                message += inputtext.charAt(i);
        }
        for (int i =0; i < message.length(); i+=2)
        {
            if (message.charAt(i) == message.charAt(i +1))
                message = message.substring(0, i+1) + 'x' + message.substring(i+1);
        }
        //make the characters of even length
        if (length % 2 ==1)
            message += 'x';
        return message;
    }

    public String[] formPairs (String message){
        int length = message.length();
        String[] pairs = new String[length/2];

        for (int i = 0, x = 0; i< length/2; i++)
            pairs[i] = message.substring(x, x+=2);
        return pairs;
    }
    public int[] getCharPos(char ch)
    {
        int[] keyPos = new int[2];
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (matrix[i][j] == ch)
                {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    break;
                }
            }
        }
        return keyPos;
    }


    @Override
    public String encrypt(String inputtext, String key) {


        inputtext = inputtext.toLowerCase();
        key = key.toLowerCase();
        String message = cleaninput(inputtext);
        generateTable(key);
        String[] msgPairs = formPairs(message);
        String encText = "";

        for (int i = 0; i < msgPairs.length; i++){
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            //if both characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]){
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            }
            //if both characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            }
            //if both characters are in different rows and columns
            else{
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }
            //get the corresponding cipher characters from the key matrix
            encText = encText + matrix[ch1Pos[0]][ch1Pos[1]] + matrix[ch2Pos[0]][ch2Pos[1]];
        }
        return encText;
    }

    @Override
    public String decrypt(String enctext, String key) {
        enctext = enctext.toLowerCase();
        key = key.toLowerCase();
        generateTable(key);
        String[] msgPairs = formPairs(enctext);
        generateTable(key);
        String dectext = "";

        for (int i = 0; i < msgPairs.length; i++){
            char ch1 = msgPairs[i].charAt(0);
            char ch2 = msgPairs[i].charAt(1);
            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            //if both characters are in the same row
            if (ch1Pos[0] == ch2Pos[0]){
                if (ch1Pos[1] > 1)
                    ch1Pos[0] = (ch1Pos[1] - 1);
                else
                    ch1Pos[0] = 4;
                if (ch2Pos[0] > 1)
                    ch2Pos[0] = (ch2Pos[1] - 1);
                else
                    ch2Pos[0] = 4;
            }
            //if both characters are in the same column
            else if (ch1Pos[1] == ch2Pos[1])
            {
                if (ch1Pos[0] > 0)
                    ch1Pos[0] = (ch1Pos[0] - 1);
                else
                    ch1Pos[0] = 4;
                if (ch2Pos[0] > 0)
                    ch2Pos[0] = (ch2Pos[0] + 1);
                else
                    ch2Pos[0] = 4;
            }
            //if both characters are in different rows and columns
            else{
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }
            //get the corresponding cipher characters from the key matrix
            dectext = dectext + matrix[ch1Pos[0]][ch1Pos[1]] + matrix[ch2Pos[0]][ch2Pos[1]];
        }
        return dectext;
    }

}
