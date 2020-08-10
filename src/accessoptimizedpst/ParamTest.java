/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @author flipp
 */
public class ParamTest extends Test{
    private ArrayList<Integer> keys;
    private int[] queryKeys;
    private double p = 0.75;
    private int expQueries;
    private int uniformQueries;
    
    @Override
    void generateQueries() {
        keys = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            keys.add(i);
        }
        Collections.shuffle(keys);
        
        queryKeys = new int[64];
        for (int i = 0; i < 64; i++){
            queryKeys[i] = keys.get(i);
        }

        numQueries = 1000000000;
        expQueries  = (int) Math.floor(numQueries*p);
        uniformQueries = numQueries - expQueries;
    }

    @Override
    void generateTrees() {
        splayTree = new SplayTree();
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Comparable> bstNodes = new ArrayList<>();

        for (int i = 0; i < numKeys; i++){
            double priority = (double)numQueries/Math.pow(2, i+1);
            int queryVal = keys.get(i);
            pstNodes.add(new PointerPSTNode(queryVal, priority));
            bstNodes.add(queryVal);
            splayTree.insert(queryVal);
        }
        
        aopst = new StaticAOPST(pstNodes);
        bst = new BalancedBST(bstNodes);
    }

    @Override
    void setName() {
        testName = "Parameterized Distribution Test: ";
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
        long r = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
        return r;
    }
    
    @Override
    public void searchAndWrite() {
        long bstTotal = 0;
        long splayTotal = 0;
        long aopstTotal = 0;
        
        for (int i = 0; i < expQueries; i++) {
            int query = queryKeys[tossCoin()];
            aopstTotal += aopst.find(query);
            bstTotal += bst.find(query);
            splayTotal += splayTree.find(query);
        }
        
        for (int i = 0; i < uniformQueries; i++){
            int query = ThreadLocalRandom.current().nextInt(0, numKeys);
            aopstTotal += aopst.find(query);
            bstTotal += bst.find(query);
            splayTotal += splayTree.find(query);
        }

        float bstAvg = (float)bstTotal/(numQueries);
        float splayAvg = (float)splayTotal/(numQueries);
        float aopstAvg = (float)aopstTotal/(numQueries);
        
        String dbSize = Integer.toString(numKeys/1000) + "k";
        System.out.printf("%-9s|%-9.5f|%-9.5f|%-9.5f|\n", dbSize, bstAvg, splayAvg, aopstAvg);
        System.out.println("_________|_________|_________|_________|");
    }
}
