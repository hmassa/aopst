package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;


public class ParetoTest extends Test {
    @Override
    void generateQueries() {
        int twentyPercent = (int) Math.floor(numKeys*0.2);
        int eightyPercent = numKeys - twentyPercent;
        
        for (int j = 0; j < eightyPercent; j++)
            queries.add(ThreadLocalRandom.current().nextInt(0, twentyPercent));
        
        for (int k = eightyPercent; k < numKeys; k++)
            queries.add(ThreadLocalRandom.current().nextInt(twentyPercent, numKeys));
        
        numQueries = queries.size();
        Collections.shuffle(queries);
    }
    
    @Override
    void generateTrees() {
        splayTree = new SplayTree();
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Comparable> bstNodes = new ArrayList<>();

        int[] counts = new int[numKeys];
        for(Comparable q : queries) {
            counts[(int)q] += 1;
        }
        
        for (int i = 0; i < numKeys; i++){
            pstNodes.add(new PointerPSTNode(i, counts[i]));
            bstNodes.add(i);
        }
        
        aopst = new StaticAOPST(pstNodes);
        bst = new BalancedBST(bstNodes);
        
        Collections.shuffle(bstNodes);
        for (Comparable i : bstNodes) {
            splayTree.insert(i);
        }
    }
    
    @Override
    void setName() {
        testName = "Pareto Distribution: ";
    }
}
