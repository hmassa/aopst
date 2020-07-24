package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;


public class ParetoTest extends Test {
    @Override
    String getSheetName() {
        return "Pareto";
    }

    @Override
    void generateQueries() {
        for (int j = 0; j < 4000; j++)
            queries.add(ThreadLocalRandom.current().nextInt(0, 200));
        
        for (int k = 0; k < 1000; k++)
            queries.add(ThreadLocalRandom.current().nextInt(200, 1000));
        
        Collections.shuffle(queries);
    }
    
    @Override
    void generateTrees() {
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Integer> bstNodes = new ArrayList<>();
        
        for (int i = 0; i < 1000; i++){
            bstNodes.add(i);
        }
//        bst = new BalancedBST(bstNodes);
        Collections.shuffle(bstNodes);
        for (int i : bstNodes) {
            splayTree.insert(i);
        }
        
        int[] counts = new int[1000];
        for(Comparable q : queries) {
            counts[(int)q] += 1;
        }
        
        for (int i = 0; i < 1000; i++){
            pstNodes.add(new PointerPSTNode(i, counts[i]));
        }
        aopst = new StaticAOPST(pstNodes);
    }

    @Override
    String getString(Comparable x) {
        Integer i = (Integer) x;
        return Integer.toString(i);
    }
}
