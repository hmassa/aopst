/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author flipp
 */
public class CoinTossTest extends Test{
    private final int maxQueries = 10000000;
    ArrayList<Integer> randomizedKeys;
    private int[] queryKeys;
    
    @Override
    void generateQueries() {
        randomizedKeys = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            randomizedKeys.add(i);
        }
        Collections.shuffle(randomizedKeys);
        
        queryKeys = new int[64];
        for (int i = 0; i < 64; i++){
            queryKeys[i] = randomizedKeys.get(i);
        }
        
        queries = new ArrayList<>();
        for (int i = 0; i < numKeys; i++){
            int queryCount = (int) Math.round(maxQueries/Math.pow(2, i+1));
            Comparable queryVal = randomizedKeys.get(i);
            for (int j = 0; j < queryCount; j++) {
                queries.add(queryVal);
            }
        }
        Collections.shuffle(queries);
        numQueries = queries.size();
    }

    @Override
    void generateTrees() {
        splayTree = new SplayTree();
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Comparable> bstNodes = new ArrayList<>();
        
        for (int i = 0; i < numKeys; i++){
            int queryCount = (int) Math.round(maxQueries/Math.pow(2, i+1));
            int queryVal = randomizedKeys.get(i);
            pstNodes.add(new PointerPSTNode(queryVal, queryCount));
            bstNodes.add(queryVal);
            splayTree.insert(queryVal);
        }
        
        aopst = new StaticAOPST(pstNodes);
        bst = new BalancedBST(bstNodes);
    }

    @Override
    void setName() {
        testName = "Coin Toss Test: ";
    }
    
    private int tossCoin() {
        int count = 0;
        long randint = lrand();

        while (randint%2 == 1) {
            count++;
            randint >>= 1;
        }
        
        return count;
    }

    private long lrand() {
        Random rand = new Random();
        long r = Math.abs(rand.nextLong());
        return r;
    }
    
    @Override
    public void searchAndWrite() {
        int bstTotal = 0;
        int splayTotal = 0;
        int aopstTotal = 0;
        for (int i = 0; i < maxQueries; i++) {
            int query = queryKeys[tossCoin()];
            aopstTotal += aopst.find(query);
            bstTotal += bst.find(query);
            splayTotal += splayTree.find(query);
        }

        float bstAvg = (float)bstTotal/(maxQueries);
        float splayAvg = (float)splayTotal/(maxQueries);
        float aopstAvg = (float)aopstTotal/(maxQueries);
        
        String dbSize = Integer.toString(numKeys/1000) + "k";
        System.out.printf("%-9s|%-9.4f|%-9.4f|%-9.4f|\n", dbSize, bstAvg, splayAvg, aopstAvg);
        System.out.println("_________|_________|_________|_________|");
    }
}
