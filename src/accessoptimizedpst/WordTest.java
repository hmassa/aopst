package accessoptimizedpst;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class WordTest extends Test{
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
    void generateTrees() {
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Comparable> bstNodes = new ArrayList<>();
        
        HashMap<String, Integer> words = new HashMap<>();
        for (Comparable q : queries){
            String word = (String) q;
            if (words.containsKey(word)){
                int value = words.get(word);
                words.replace(word, value+1);
            } else {
                words.put(word, 1);
            }
        }
        
            for (HashMap.Entry<String, Integer> pair : words.entrySet()) {
                pstNodes.add(new PointerPSTNode(pair.getKey(), pair.getValue()));
                bstNodes.add(pair.getKey());
            }
        
        bst = new BalancedBST(bstNodes);
        aopst = new StaticAOPST(pstNodes);
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

