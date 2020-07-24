package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;

public class ZipfTest extends Test{
    private int size = 100;
    private double skew = 1.07d;

    @Override
    String getSheetName() {
        return "Zipf";
    }

    @Override
    void generateQueries() {
        int baseNum = 500;
        for (int i = 1; i <= size; i++){
            int rankNum = (int)Math.round(baseNum/(Math.pow(i, skew)));
            for (int j = 0; j < rankNum; j++){
                queries.add(i);
            }
        }
        
        Collections.shuffle(queries);
    }
    
    @Override
    void generateTrees() {
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Integer> bstNodes = new ArrayList<>();
        
        for (int i = 1; i <= size; i++){
            bstNodes.add(i);
        }
//        bst = new BalancedBST(bstNodes);
        Collections.shuffle(bstNodes);
        for (int i : bstNodes) {
            splayTree.insert(i);
        }
        
        int[] counts = new int[size];
        for(Comparable q : queries) {
            counts[(int)q - 1] += 1;
        }
        
        for (int i = 0; i < size; i++){
            pstNodes.add(new PointerPSTNode(i+1, counts[i]));
        }
        aopst = new StaticAOPST(pstNodes);
    }

    @Override
    String getString(Comparable x) {
        Integer i = (Integer) x;
        return Integer.toString(i);
    }
}