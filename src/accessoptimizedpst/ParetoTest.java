package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;


public class ParetoTest extends Test {
    @Override
    void generateQueries() {
        queries = new ArrayList<>();
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
