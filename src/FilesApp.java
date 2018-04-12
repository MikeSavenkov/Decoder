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

        File f = new File("C:\\Users\\mv.savenkov\\Desktop\\ResultCountCharacters.txt");
        //File f1 = new File("../AnalyseData/ResultCountCombination.txt");

            countCharacters(text, f);

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

    public static String countCharacters(String text, File f) throws IOException {



        try {

            if(!f.exists()){

                f.createNewFile();

            }

            PrintWriter pw = new PrintWriter(f.getAbsoluteFile());

            try {

                pw.println("\n" + "№     Буква   Абс.частота    Доля        Доля с пробелом    ");

                int countSpace = frequency(" ", text);
                //long textSize = text.length();
                //long textSizeWithoutSpaces = textSize - countSpace;

                float countIndex, countIndexSpace, index = 0, indexSpace = 0;
                long count = 0, textSizeWithoutSpaces2 = 0;

                for (int j = 1040; j < 1072; j++) {

                    char symbol = (char) j;
                    count = frequency(String.valueOf((char) j), text) + frequency(String.valueOf((char) (j + 32)), text);
                    textSizeWithoutSpaces2 = textSizeWithoutSpaces2  + count;
                }

                count = 0;
                long textSizeWithSpaces2 = textSizeWithoutSpaces2 + countSpace;

                int numberCharacter = 0;

                for (int i = 1040; i < 1072; i++) {

                    numberCharacter++;

                    char symbol = (char) i;
                    count = frequency(String.valueOf((char) i), text) + frequency(String.valueOf((char) (i + 32)), text);

                    //count1 = count1  + count;


                    double probability = new BigDecimal((double) count / (textSizeWithoutSpaces2)).setScale(4, RoundingMode.UP).doubleValue();
                    double probabilityPlusSpace = new BigDecimal((double) count / (textSizeWithSpaces2)).setScale(4, RoundingMode.UP).doubleValue();

                    countIndex = ((float) count * (count - 1)) / (textSizeWithoutSpaces2 * (textSizeWithoutSpaces2 - 1));
                    index = index + countIndex;
                    countIndexSpace = ((float) count * (count - 1)) / (textSizeWithSpaces2 * (textSizeWithSpaces2 - 1));
                    indexSpace = indexSpace + countIndexSpace;




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
                pw.println("Символов без пробелов            " + textSizeWithoutSpaces2);
                pw.println("Символов с пробелами             " + textSizeWithSpaces2);
                pw.println("Индекс совпадения без пробелов   " + new BigDecimal(index).setScale(4, RoundingMode.UP).doubleValue());
                pw.println("Индекс совпадения с пробелами    " + new BigDecimal(indexSpace).setScale(5, RoundingMode.UP).doubleValue());







            }
            finally {
                pw.close();
            }
        } catch(IOException e) {
            throw new RuntimeException();
        }



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
