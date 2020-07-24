package accessoptimizedpst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CharacterTest extends Test{
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
    void generateTrees() {
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Character> bstNodes = new ArrayList<>();
        
        for (int i = 0; i < 26; i++){
            bstNodes.add((char)(i + 97));
        }
//        bst = new BalancedBST(bstNodes);
        Collections.shuffle(bstNodes);
        for (char c : bstNodes){
            splayTree.insert(c);
        }
        
        int[] charCounts = new int[26];
        for (Comparable q : queries){
            int index = Character.getNumericValue((char)q) - 97;
            if (index >= 0 && index < 26){
                charCounts[index]+= 1;                
            }
        }
        
        for (int i = 0; i < 26; i++){
            pstNodes.add(new PointerPSTNode((char)(i+97) , charCounts[i]));
        }
        
        aopst = new StaticAOPST(pstNodes);
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