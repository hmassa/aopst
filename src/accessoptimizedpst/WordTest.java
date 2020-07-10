package accessoptimizedpst;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class WordTest extends Test{
    @Override
    void generateKeys() {
        File wordList = new File("google-10000-english.txt");
        try {
            Scanner sc = new Scanner(wordList);
            while (sc.hasNextLine()){
                String word = sc.nextLine();
                keys.add(word);
            }   
        } catch (FileNotFoundException ex) {
            System.exit(-1);
        }
    }

    @Override
    void generateQueries() {
        PDDocument doc;
        try {
            doc = PDDocument.load(new File("star-wars-episode-iv-a-new-hope-1977.pdf"));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String[] scriptWords = pdfStripper.getText(doc).split("\\W+");
            for (String sw : scriptWords)
                queries.add(sw.toLowerCase());
        } catch (IOException ex) {
            System.out.println("The script file could not be found.");
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
        return "Script";
    }
}

