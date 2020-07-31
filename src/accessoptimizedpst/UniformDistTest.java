package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author flipp
 */
public class UniformDistTest extends Test { 
    @Override
    void generateQueries() {
        queries = new ArrayList<>();
        for (int i = 0; i < numKeys; i++){
            queries.add(i);
        }
        numQueries = queries.size();
        Collections.shuffle(queries);
    }
    
    @Override
    void generateTrees() {
        splayTree = new SplayTree();
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
    
    @Override
    public void searchAndWrite() {
        long bstTotal = 0;
        long splayTotal = 0;
        long aopstTotal = 0;
        for (Comparable query : queries) {
            aopstTotal += aopst.find(query);
            bstTotal += bst.find(query);
            splayTotal += splayTree.find(query);
        }
        float bstAvg = (float)bstTotal/(numQueries);
        float splayAvg = (float)splayTotal/(numQueries);
        float aopstAvg = (float)aopstTotal/(numQueries);
        
        String dbSize = Integer.toString(numKeys/1000) + "k";
        System.out.printf("%-9s|%-9.4f|%-9.4f|%-9.4f|\n", dbSize, bstAvg, splayAvg, aopstAvg);
        System.out.println("_________|_________|_________|_________|");
    }
}
