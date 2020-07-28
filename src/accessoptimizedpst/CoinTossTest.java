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
    private final int maxQueries = 1000000;
    private final ArrayList<Comparable> randomizedKeys = new ArrayList<>();
    
    @Override
    void generateQueries() {
        for (int i = 1; i <= numKeys; i++) {
            randomizedKeys.add(i);
        }
        Collections.shuffle(randomizedKeys);
        
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
        ArrayList<PointerPSTNode> pstNodes = new ArrayList<>();
        ArrayList<Comparable> bstNodes = new ArrayList<>();
        
        for (int i = 0; i < numKeys; i++){
            int queryCount = (int) Math.round(maxQueries/Math.pow(2, i+1));
            Comparable queryVal = randomizedKeys.get(i);
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
}
