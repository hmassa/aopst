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
    private ArrayList<PointerPSTNode> restNodes;
    private int[] queryKeys;
    private double p = 1;
    private int expQueries;
    private int uniformQueries;
    
    @Override
    void generateQueries() {
        keys = new ArrayList<>();
        restNodes = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            keys.add(i);
            restNodes.add(new PointerPSTNode(i, 0));
        }
        Collections.shuffle(keys);
        
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
        splayTree = new SplayTree();
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();

        for (int i = 0; i < numKeys; i++){
            double priority = (double)numQueries/Math.pow(2, i+1);
            int queryVal = keys.get(i);
            pstNodes.add(new PointerPSTNode(queryVal, priority));
            splayTree.insert(queryVal);
        }
        
        stat = new StaticAOPST(pstNodes);
        rest = new RestructuringAOPST(restNodes);
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
        long splayTotal = 0;
        long restTotal = 0;
        long statTotal = 0;
        
        for (int i = 0; i < expQueries; i++) {
            int query = queryKeys[tossCoin()];
            restTotal += rest.find(query);
            statTotal += stat.find(query);
            splayTotal += splayTree.find(query);
        }
        
        for (int i = 0; i < uniformQueries; i++){
            int query = ThreadLocalRandom.current().nextInt(0, numKeys);
            restTotal += rest.find(query);
            statTotal += stat.find(query);
            splayTotal += splayTree.find(query);
        }

        float restAvg = (float)restTotal/(numQueries);
        float statAvg = (float)statTotal/(numQueries);
        float splayAvg = (float)splayTotal/(numQueries);
        
        String dbSize = Integer.toString(numKeys/1000) + "k";
        System.out.printf("%-9s|%-9.5f|%-9.5f|%-9.5f|\n", dbSize, splayAvg, restAvg, statAvg);
        System.out.println("_________|_________|_________|_________|");
    }
}
