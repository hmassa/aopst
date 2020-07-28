package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author flipp
 */
public class UniformDistTest extends Test { 
    @Override
    void generateQueries() {
        for (int i = 0; i < numKeys; i++){
            queries.add(i);
        }
        numQueries = queries.size();
        Collections.shuffle(queries);
    }
    
    @Override
    void generateTrees() {
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Comparable> bstNodes = new ArrayList<>();

        for (int i = 0; i < numKeys; i++){
            pstNodes.add(new PointerPSTNode(i, 1));
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
        testName = "Uniform Distribution";
    }
}
