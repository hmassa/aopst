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
 *
 * @author flipp
 */
public class SplayWorstCaseTest extends Test {  
    private ArrayList<Integer> keys;
    private Integer query;
    
    @Override
    void generateQueries() {
        query = keys.get(0);
    }

    @Override
    void generateTrees() {
        keys = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            keys.add(i);
        }
        
        bst = new BalancedBST(keys);
        rest = new RestructuringAOPST(keys);
        splayTree = new SplayTree();

        for (int i = 0; i < numKeys; i++){
            splayTree.insert(keys.get(i));
        }
    }

    @Override
    void setName() {
        testName = "Splay Tree Worst Case - O(n) search";
    }
    
    @Override
    public void searchAndWrite() {
        int splayCount = splayTree.find(query);
        int restCount = rest.find(query);
        int bstCount = bst.find(query);
        
        String dbSize = Integer.toString(numKeys/1000) + "k";
        System.out.printf("%-9s|%-9s|%-9s|%-9s|\n", dbSize, splayCount, restCount, bstCount);
        System.out.println("_________|_________|_________|_________|");
    }
}
