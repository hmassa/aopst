package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;

public class ZipfTest extends Test{
    private double skew = 1.07d;
    
    @Override
    void generateQueries() {
        queries = new ArrayList<>();
        int baseNum = numKeys/2;
        for (int i = 1; i <= numKeys; i++){
            int rankNum = (int)Math.round(baseNum/(Math.pow(i, skew)));
            for (int j = 0; j < rankNum; j++){
                queries.add(i);
            }
        }
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
            counts[(int)q - 1] += 1;
        }
        
        for (int i = 0; i < numKeys; i++){
            pstNodes.add(new PointerPSTNode(i+1, counts[i]));
            bstNodes.add(i+1);
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
        testName = "Zipf Distribution";
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