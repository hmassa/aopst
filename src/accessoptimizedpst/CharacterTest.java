package accessoptimizedpst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;

public class CharacterTest extends Test{
    @Override
    void generateKeys() {
        for (int i = 0; i < 26; i++)
            keys.add((char)(i + 97));
        
        Collections.shuffle(keys);
    }

    @Override
    void generateQueries() {
        String queryFileName = "lyrics.txt";
        File queryFile = new File(queryFileName);

        try {
            Scanner sc = new Scanner(queryFile);
            while (sc.hasNextLine()){
                char[] chars = sc.nextLine().toCharArray();
                for(char c : chars)
                    queries.add(Character.toLowerCase(c));   
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The character file was not found.");
            System.exit(-1);
        }
        
    }

    @Override
    String getString(Comparable x) {
        String str = String.valueOf(x);
        return str;
    }

    @Override
    String getSheetName() {
        return "Lyrics";
    }
}