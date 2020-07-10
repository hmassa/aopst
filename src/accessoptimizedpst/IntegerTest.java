package accessoptimizedpst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class IntegerTest extends Test {
    @Override
    void generateKeys() {
        for (int i = 1; i < 10; i++)
            keys.add(i);
        Collections.shuffle(keys);
    }

    @Override
    String getSheetName() {
        return "Population";
    }

    @Override
    void generateQueries() {
        String queryFileName = "population.txt";
        File queryFile = new File(queryFileName);
        
        try {
            Scanner sc = new Scanner(queryFile);
            while (sc.hasNextLine()){
                char[] chars = sc.nextLine().toCharArray();
                queries.add(Character.getNumericValue(chars[0]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("The integer file could not be found.");
            System.exit(-1);
        }
        
        Collections.shuffle(queries);
    }

    @Override
    String getString(Comparable x) {
        Integer i = (Integer) x;
        return Integer.toString(i);
    }
}
