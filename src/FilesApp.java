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


        String text = readFile("C:\\Users\\mv.savenkov\\Desktop\\allTexts.txt", StandardCharsets.UTF_8);
        File file = new File("C:\\Users\\mv.savenkov\\Desktop\\ResultCountCharacters.txt");
        countCharacters(text, file);

        //String encodedText = readFile("C:\\Users\\mv.savenkov\\Desktop\\encoded.txt", StandardCharsets.UTF_8);
        //File encodedfile = new File("C:\\Users\\mv.savenkov\\Desktop\\CountCharactersInEncodedFile.txt");
        //countCharactersInEncodedFile(encodedText, encodedfile);

        //File f1 = new File("../AnalyseData/ResultCountCombination.txt");

        //countCombination(text, f1);

    }



    public static String countCombination(String text, File f1) throws IOException {

        try {

            if(!f1.exists()){

                f1.createNewFile();

            }

            PrintWriter pw = new PrintWriter(f1.getAbsoluteFile());

            try {

        ArrayList<String> doubleCharacters = new ArrayList<String>();
        ArrayList<String> bigDoubleCharacters = new ArrayList<String>();


        pw.println("\n" + "№      сочетание    Абс. частота");

        int numberCombination = 1;
        int k;
        for (int i = 1072; i < 1104; i++){
            for (int j = 1072; j < 1104; j++) {
                for (k = 0; k < doubleCharacters.size() ; k++) {

                }

                String bigDoubleCharacter = (char)(i-32) + "" + (char)j;
                String doubleCharacter = (char)i + "" + (char)j;

                doubleCharacters.add(doubleCharacter);
                bigDoubleCharacters.add(bigDoubleCharacter);

                if (((frequency(doubleCharacters.get(k), text) != 0) & (frequency(bigDoubleCharacters.get(k), text) != 0)) |
                   ((frequency(doubleCharacters.get(k), text) == 0) & (frequency(bigDoubleCharacters.get(k), text) != 0)) |
                   ((frequency(doubleCharacters.get(k), text) != 0) & (frequency(bigDoubleCharacters.get(k), text) == 0))){

                    if (numberCombination > 9 & numberCombination < 100){
                        pw.println(numberCombination + "   |  " + doubleCharacters.get(k) + "        |  " + (frequency(doubleCharacters.get(k), text) + frequency(bigDoubleCharacters.get(k), text)));
                    }
                    else if (numberCombination > 99 & numberCombination < 999){
                        pw.println(numberCombination + "  |  " + doubleCharacters.get(k) + "        |  " + (frequency(doubleCharacters.get(k), text) + frequency(bigDoubleCharacters.get(k), text)));
                    }
                    else {
                        pw.println(numberCombination + "    |  " + doubleCharacters.get(k) + "        |  " + (frequency(doubleCharacters.get(k), text) + frequency(bigDoubleCharacters.get(k), text)));
                    }
                    numberCombination++;
                }

                else if ((frequency(doubleCharacters.get(k), text) == 0) & (frequency(bigDoubleCharacters.get(k), text) == 0)){
                    continue;
                }
            }

        }
            }
            finally {
                pw.close();
            }
        }
        catch(IOException e) {
            throw new RuntimeException();
        }


        return " ";

    }

    public static void countCharactersInEncodedFile(String encodedText, File encodedFile) {

        HashMap<Character, Long> unsortMap = new HashMap<>();

        try {
            if (!encodedFile.exists()) {
                encodedFile.createNewFile();
            }
            PrintWriter pw = new PrintWriter(encodedFile.getAbsoluteFile());
            long count = 0, textSize = 0;
            for (int j = 1040; j < 1072; j++) {

                count = frequency(String.valueOf((char) j), encodedText) + frequency(String.valueOf((char) (j + 32)), encodedText);
                textSize = textSize  + count;
            }

            for (int i = 1040; i < 1072; i++) {
                char symbol = (char) i;
                count = frequency(String.valueOf((char) i), encodedText) + frequency(String.valueOf((char) (i + 32)), encodedText);
                unsortMap.put(symbol, count);

            }
            final Map<Character, Long> sortMap = new LinkedHashMap<>();
            unsortMap.entrySet().stream()
                     .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                     .forEachOrdered(x -> sortMap.put(x.getKey(), x.getValue()));

            for (Map.Entry<Character, Long> entry: sortMap.entrySet()) {
                pw.println(entry.getKey() + ": " + entry.getValue());
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

    }




    public static void countCharacters(String text, File file) throws IOException {

        String encodedText = readFile("C:\\Users\\mv.savenkov\\Desktop\\encoded.txt", StandardCharsets.UTF_8);
        //File encodedfile = new File("C:\\Users\\mv.savenkov\\Desktop\\CountCharactersInEncodedFile.txt");

        HashMap<Character, Long> unsortMap = new HashMap<>();
        HashMap<Character, Long> unsortMapEncoded = new HashMap<>();

        String textUpperCase = text.toUpperCase();

        try {

            PrintWriter pw = new PrintWriter(file.getAbsoluteFile());

            try {

                pw.println("\n" + "№     Буква   Абс.частота    Доля        Доля с пробелом    ");

                long countSpace = frequency(" ", textUpperCase);
                long countComma = frequency(",", encodedText);

                float countIndex, countIndexSpace, index = 0, indexSpace = 0;
                long count = 0, countInEncoded = 0;
                long textSizeWithoutSpaces = 0, textSizeInEncoded = 0;

                for (int j = 1040; j < 1072; j++) {

                    char symbol = (char) j;
                    count = frequency(String.valueOf(symbol), textUpperCase);
                    textSizeWithoutSpaces = textSizeWithoutSpaces  + count;

                }

                for (int j = 1040; j < 1072; j++) {

                    char symbol = (char) j;
                    countInEncoded = frequency(String.valueOf(symbol), encodedText);
                    textSizeInEncoded = textSizeInEncoded  + countInEncoded;

                }


                unsortMap.put(' ', countSpace);
                unsortMapEncoded.put(',', countComma);
                count = 0;
                countInEncoded = 0;

                long textSizeWithSpaces = textSizeWithoutSpaces + countSpace;
                int numberCharacter = 0;

                for (int i = 1040; i < 1072; i++) {

                    numberCharacter++;

                    char symbol = (char) i;
                    count = frequency(String.valueOf(symbol), textUpperCase);
                    countInEncoded = frequency(String.valueOf(symbol), encodedText);

                    unsortMap.put(symbol, count);
                    unsortMapEncoded.put(symbol, countInEncoded);

                    double probability = new BigDecimal((double) count / (textSizeWithoutSpaces)).setScale(4, RoundingMode.UP).doubleValue();
                    double probabilityPlusSpace = new BigDecimal((double) count / (textSizeWithSpaces)).setScale(4, RoundingMode.UP).doubleValue();

                    countIndex = ((float) count * (count - 1)) / (textSizeWithoutSpaces * (textSizeWithoutSpaces - 1));
                    index = index + countIndex;
                    countIndexSpace = ((float) count * (count - 1)) / (textSizeWithSpaces * (textSizeWithSpaces - 1));
                    indexSpace = indexSpace + countIndexSpace;



                    //formatting
                    if (numberCharacter <= 9) {
                        pw.println(numberCharacter + "   |  " + symbol + "    |  " + count + "\t" + "    |  " + probability + "\t" + " |  " + probabilityPlusSpace);
                    } else if (numberCharacter > 9 & (count < 300000 & count > 10000)) {
                        pw.println(numberCharacter + "  |  " + symbol + "    |  " + count + "\t" + "    |  " + probability + "\t" + " |  " + probabilityPlusSpace);
                    } else if (numberCharacter > 9 & count < 10000) {
                        pw.println(numberCharacter + "  |  " + symbol + "    |  " + count + "\t" + "    |  " + probability + "\t" + " |  " + probabilityPlusSpace);
                    }


                }


                pw.println();
                pw.println("\n" + "Пробелов                         " + countSpace);
                pw.println("Символов без пробелов            " + textSizeWithoutSpaces);
                pw.println("Символов с пробелами             " + textSizeWithSpaces);
                pw.println("Индекс совпадения без пробелов   " + new BigDecimal(index).setScale(4, RoundingMode.UP).doubleValue());
                pw.println("Индекс совпадения с пробелами    " + new BigDecimal(indexSpace).setScale(5, RoundingMode.UP).doubleValue());

                final Map<Character, Long> sortMap = new LinkedHashMap<>();
                unsortMap.entrySet().stream()
                         .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                         .forEachOrdered(x -> sortMap.put(x.getKey(), x.getValue()));

                final Map<Character, Long> sortMapEncoded = new LinkedHashMap<>();
                unsortMapEncoded.entrySet().stream()
                         .sorted(Map.Entry.<Character, Long>comparingByValue().reversed())
                         .forEachOrdered(x -> sortMapEncoded.put(x.getKey(), x.getValue()));

                List<Character> charsEncoded = new ArrayList<>();
                for (Character key: sortMapEncoded.keySet()) {
                    charsEncoded.add(key);
                }

                List<Character> chars = new ArrayList<>();
                for (Character key: sortMap.keySet()) {
                    chars.add(key);
                }
                System.out.println("сортировка букв в порядке убывания по частоте в обычном тексте:");
                System.out.println(chars);
                System.out.println("сортировка букв в порядке убывания по частоте в зашифрованном тексте:");
                System.out.println(charsEncoded);
                System.out.println();
                
//                System.out.println(encodedText);
//                String string = null;
//                for (int j = 0; j < 32; j++) {
//
//                    string = encodedText.replace(charsEncoded.get(j), chars.get(j));
//                    encodedText = string;
//
//                }
//                System.out.println();
//                String decodedText = encodedText;
//                System.out.println(decodedText);


                char[] charArray = encodedText.toCharArray();
                for (int k = 0; k < charArray.length; k++) {
                    for (int l = 0; l < chars.size(); l++) {
                        if (charArray[k] == charsEncoded.get(l)) {
                            charArray[k] = chars.get(l);
                            break;
                        }
                    }
                }

                System.out.println(encodedText);
                System.out.println();
                String decoded = new String(charArray);
                System.out.println(decoded);


                for (Map.Entry<Character, Long> entry: sortMap.entrySet()) {
                    pw.println(entry.getKey() + ": " + entry.getValue());
                }

                pw.println();
                for (Map.Entry<Character, Long> entry: sortMapEncoded.entrySet()) {
                    pw.println(entry.getKey() + ": " + entry.getValue());
                }


            }
            finally {
                pw.close();
            }
        } catch(IOException e) {
            throw new RuntimeException();
        }
    }





    private static int frequency(String symbol, String file) {

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


    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));        //encoded - закодированный
        return new String(encoded, encoding);
    }



}
