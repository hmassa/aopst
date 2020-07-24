package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * @author flipp
 */
public class NormalDistTest extends Test{
    @Override
    void generateQueries() {
        Random ran = new Random();
        for (int j = 0; j < 3000; j++){
            double val = ran.nextGaussian()*100 + 500;
            queries.add((int)Math.round(val));
        }
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

    @Override
    String getSheetName() {
        return "Normal Dist";
    }
}
