/**
 * Created by mikesavenkov on 3/16/16.
 */

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FilesApp {

    public static void main(String[] args) throws IOException {

        //String text = readFile("/Users/mikesavenkov/Desktop/3.txt", StandardCharsets.UTF_8);
//        String[] alphabet = new String[]{"ё","Ё","В","Г","Д","е","Ё","Ж","З","И","Й","К","Л","М","Н","О","П","Р","С","Т",
//
//                "У","Ф","Х","Ц","Ч","Ш","Щ", "Ъ","Ы","Ь","Э","Ю","Я"," "};
//                                                                                                // 1040-1103 - code of alphabet in unicode
//        for (String j: alphabet) {                   //перевод букв в коды
//            char c = j.charAt(0);
//            int ascii = (int)c;
//            System.out.println(" " + ascii );
//        }


        System.out.println(" ");
        System.out.println("№   | Буква | Абс.частота | Вероятность | Вероятность с пробелом");

        countCharacters();

        countCombination();

    }

    public static String countCombination() throws IOException {

        String text = readFile("/Users/mikesavenkov/Desktop/5.txt", StandardCharsets.UTF_8);

        ArrayList<String> doubleCharacters = new ArrayList<String>();
        ArrayList<String> bigDoubleCharacters = new ArrayList<String>();
        ArrayList<String> doubleCharactersE = new ArrayList<String>();
        ArrayList<String> doubleCharactersEE = new ArrayList<String>();


        System.out.println("сочетание    Абс. частота");

        int k = 0;
        int i;
        for (i = 1072; i < 1104; i++){
            if (i == 1078) {
                int m;
                for (int l = 1105; l < 1106; l++) {
                    for (int n = 1072; n < 1104; n++) {
                        for (m = 0; m < doubleCharactersE.size(); m++) {

                        }

                        String doubleCharacterE = (char) l + "" + (char) n;
                        String doubleCharacterEE = (char) (l - 80) + "" + (char) n;

                        doubleCharactersE.add(doubleCharacterE);
                        doubleCharactersEE.add(doubleCharacterEE);

                        System.out.println(" " + doubleCharacterE + "           " + ((frequency(doubleCharactersE.get(m), text)) + (frequency(doubleCharactersEE.get(m), text))));

                    }

                }

            }

            for (int j = 1072; j < 1104; j++) {
                for (k = 0; k < doubleCharacters.size() ; k++) {

                }
                String bigDoubleCharacter = (char)(i-32) + "" + (char)j;
                String doubleCharacter = (char)i + "" + (char)j;
                doubleCharacters.add(doubleCharacter);
                bigDoubleCharacters.add(bigDoubleCharacter);




                System.out.println(" " + doubleCharacters.get(k) + "           " + (frequency(doubleCharacters.get(k), text) + frequency(bigDoubleCharacters.get(k), text)));
            }

        }
        return " ";

    }

    public static String countCharacters() throws IOException {

        String text = readFile("/Users/mikesavenkov/Desktop/tests.txt", StandardCharsets.UTF_8);

        //int countE = frequency("Ё", text) + frequency("ё", text);
        int countSpace = frequency(" ",text);
        long textSize = text.length();
        long textSizeWithoutSpaces = textSize - countSpace;
        float countIndex,countIndexSpace,index = 0,indexSpace = 0;
        long count = 0;

        //Map<Long, Character>  test = new HashMap<>();
        //List<Long>   counters = new ArrayList<>();
        int numberCharacter = 0;
        for (int i = 1040; i < 1072; i++) {     //1040-1072

            numberCharacter++;

            char symbol = (char)i;
            count = frequency(String.valueOf((char) i), text) + frequency(String.valueOf((char) (i + 32)), text);

            double probability = new BigDecimal((double) count/(textSize)).setScale(4, RoundingMode.UP).doubleValue();
            double probabilityPlusSpace = new BigDecimal((double) count/(textSizeWithoutSpaces)).setScale(4, RoundingMode.UP).doubleValue();

            countIndex = ((float) count * (count - 1)) / (textSize * (textSize - 1));
            index = index + countIndex;
            countIndexSpace = ((float) count * (count - 1)) / (textSizeWithoutSpaces * (textSizeWithoutSpaces - 1));
            indexSpace = indexSpace + countIndexSpace;

            char symbol1 = 0;
            int countE = 0;
            if (i==1046){
                for (int j = 1105; j < 1106; j++) {
                    symbol1 = (char) j;
                    countE = frequency(String.valueOf((char) j), text) + frequency(String.valueOf((char) j - 80), text);
                }
                double probabilityE = new BigDecimal((double) countE/(textSize)).setScale(4, RoundingMode.UP).doubleValue();
                double probabilityPlusSpaceE = new BigDecimal((double) countE/(textSizeWithoutSpaces)).setScale(4, RoundingMode.UP).doubleValue();

                countIndex = ((float) countE * (countE - 1)) / (textSize * (textSize - 1));
                index = index + countIndex;
                countIndexSpace = ((float) countE * (countE - 1)) / (textSizeWithoutSpaces * (textSizeWithoutSpaces - 1));
                indexSpace = indexSpace + countIndexSpace;


                System.out.println(numberCharacter + "   |" + symbol1 + "       | " + countE + "\t\t\t\t" + probabilityE + "\t\t\t" +probabilityPlusSpaceE);
                numberCharacter++;
            }


            if (numberCharacter > 9) {
                System.out.println(numberCharacter + "  | " + symbol + "     | " + count + "\t\t\t" + probability + "\t\t\t" + probabilityPlusSpace);
            }
            else {
                System.out.println(numberCharacter + "   | " + symbol + "     | " + count + "\t\t\t" + probability + "\t\t\t" + probabilityPlusSpace);

            }


            //test.put(count, symbol);
            //counters.add(count);

        }


        //Collections.sort(counters);
        //Map<Character, Integer> result = new HashMap<>();

//        for (int i = 0; i < counters.size(); i++) {
//             result.put(test.get(counters.get(i)), i++);
//        }
//
//        for(Map.Entry<Character, Integer> r: result.entrySet()) {
//            System.out.println(r.getKey() + " " + r.getValue());
//        }

        System.out.println("всего символов " + text.length());
        System.out.println("количество пробелов " + countSpace);
        System.out.println("индекс выпадения " + new BigDecimal(index).setScale(4, RoundingMode.UP).doubleValue() + "\n" + "индекс выпадения с пробелами " + new BigDecimal(indexSpace).setScale(5, RoundingMode.UP).doubleValue() );
        System.out.println(frequency("Ё", text) + frequency("ё", text));


        return " ";
    }





    public static int frequency(String symbol, String file) {
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){

            lastIndex = file.indexOf(symbol,lastIndex);    //indexOf search symbol from the position lastIndex

            if(lastIndex != -1){
                count ++;
                lastIndex += symbol.length();
            }
        }
        return count;
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));        //encoded - закодированный
        return new String(encoded, encoding);
    }



}