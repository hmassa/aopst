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
    private double p = 1;
    private int expQueries;
    private int uniformQueries;
    
    @Override
    void generateQueries() {
        keys = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            keys.add(i);
        }
//        Collections.shuffle(keys);
        
        queryKeys = new int[64];
        for (int i = 0; i < 64; i++){
            queryKeys[i] = keys.get(i);
        }

        numQueries = 1000000;
        expQueries  = (int) Math.floor(numQueries*p);
        uniformQueries = numQueries - expQueries;
    }

    @Override
    void generateTrees() {
        bst = new BalancedBST(keys);
        rest = new RestructuringAOPST(keys);
        splayTree = new SplayTree();

        for (int i = 0; i < numKeys; i++){
            int queryVal = keys.get(i);
            splayTree.insert(queryVal);
        }
    }

    @Override
    void setName() {
        testName = "Parameterized Distribution Test - P = " + p;
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
        long splayTotal = 0;
        long restTotal = 0;
        long bstTotal = 0;
        
        for (int i = 0; i < expQueries; i++) {
            int query = queryKeys[tossCoin()];
            restTotal += rest.find(query);
            bstTotal += bst.find(query);
            splayTotal += splayTree.find(query);
        }
        
        for (int i = 0; i < uniformQueries; i++){
            int query = ThreadLocalRandom.current().nextInt(0, numKeys);
            
            if (restTotal*bstTotal*splayTotal > 0) {
                restTotal += rest.find(query);
                bstTotal += bst.find(query);
                splayTotal += splayTree.find(query);
            } else {
                System.out.println("out of range");
                return;
            }
        }

        float restAvg = (float)restTotal/(numQueries);
        float bstAvg = (float)bstTotal/(numQueries);
        float splayAvg = (float)splayTotal/(numQueries);
        
        String dbSize = Integer.toString(numKeys/1000) + "k";
        System.out.printf("%-9s|%-9.5f|%-9.5f|%-9.5f|\n", dbSize, splayAvg, restAvg, bstAvg);
        System.out.println("_________|_________|_________|_________|");
    }
}
