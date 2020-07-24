package accessoptimizedpst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class IntegerTest extends Test {
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

    @Override
    void generateTrees() {
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Comparable> bstNodes = new ArrayList<>();
        
        for (int i = 1; i < 10; i++){
            bstNodes.add(i);
        }
        bst = new BalancedBST(bstNodes);
        
        int[] counts = new int[9];
        for(Comparable q : queries) {
            counts[(int)q - 1] += 1;
        }
        
        for (int i = 0; i < 9; i++){
            pstNodes.add(new PointerPSTNode(i+1, counts[i]));
        }
        aopst = new StaticAOPST(pstNodes);
    }
}
