package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author flipp
 */
public class UniformDistTest extends Test { 
    @Override
    void generateQueries() {
        for (int i = 0; i < 1000; i++){
            queries.add(i);
            queries.add(i);
            queries.add(i);
        }
        Collections.shuffle(queries);
    }
    
    @Override
    void generateTrees() {
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Integer> bstNodes = new ArrayList<>();

        for (int i = 0; i < 1000; i++){
            pstNodes.add(new PointerPSTNode(i, 3));
            bstNodes.add(i);
        }
        aopst = new StaticAOPST(pstNodes);
//        bst = new BalancedBST(bstNodes);
        Collections.shuffle(bstNodes);
        for (int i : bstNodes) {
            splayTree.insert(i);
        }
    }

    @Override
    String getString(Comparable x) {
        Integer i = (Integer) x;
        return Integer.toString(i);
    }

    @Override
    String getSheetName() {
        return "Uniform Dist";
    }
}
