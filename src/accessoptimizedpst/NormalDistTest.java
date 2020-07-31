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
        queries = new ArrayList<>();
        int mid = numKeys/2;
        int range = numKeys/10;
        Random ran = new Random();
        for (int j = 0; j < numKeys; j++){
            double val = ran.nextGaussian()*range + mid;
            if (val >= 0 && val < numKeys) {
               queries.add((int)Math.round(val)); 
            }
            numQueries = queries.size();
        }
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
        testName = "Normal Distribution: ";
    }
    
    @Override
    public void searchAndWrite() {
        int bstTotal = 0;
        int splayTotal = 0;
        int aopstTotal = 0;
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
