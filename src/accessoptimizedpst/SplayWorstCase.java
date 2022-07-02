/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accessoptimizedpst;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author flipp
 */
public class SplayWorstCase extends Test {
    private ArrayList<Integer> keys;
    private int[] queryKeys;
    private double p = 0.9;
    
     @Override
    void generateTrees() {
        keys = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            keys.add(i);
        }
        
        bst = new BalancedBST(keys);
        rest = new RestructuringAOPST(keys);
        splayTree = new SplayTree();
        
        Collections.shuffle(keys);

        for (int i = 0; i < numKeys; i++){
            int queryVal = keys.get(i);
            splayTree.insert(queryVal);
        }
    }
    
    @Override
    void generateQueries() {
        Collections.shuffle(keys);
        
        queryKeys = new int[64];
        for (int i = 0; i < 64; i++){
            queryKeys[i] = keys.get(i);
        }

        numQueries = 1000000000;
    }
    
    @Override
    void setName() {
        testName = "Splay Worst Case Test";
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
        int splayHold, restHold, bstHold;
        int splayMax = 0, restMax = 0, bstMax = 0;
        long splayTotal = 0, restTotal = 0, bstTotal = 0;

        File avgFile, maxFile;
        FileWriter avgFw, maxFw;
        int interval = 5000000;

        int query;
        int random;
        
        String line;
        
        try {
            maxFile = new File("./Results/output.txt");
            maxFw = new FileWriter(maxFile);
            maxFw.write("Count,AAPST,Splay,BST\n");

            avgFile = new File("./Results/average.txt");
            avgFw = new FileWriter(avgFile);
            avgFw.write("Count,AAPST,Splay,BST\n");
            
            for (int i = 0; i < numQueries/interval; i++) {
                for (int j = 0; j < interval; j++) {
                    random = ThreadLocalRandom.current().nextInt(0, 100);
                    if (random > p*100) {
                        query = ThreadLocalRandom.current().nextInt(1, numKeys);
                    } else {
                        query = queryKeys[tossCoin()];
                    }

                    restHold = rest.find(query);
                    bstHold = bst.find(query);
                    splayHold = splayTree.find(query);

                    if (restHold*bstHold*splayHold > 0) {
                        restTotal += restHold;
                        splayTotal += splayHold;
                        bstTotal += bstHold;
                        
                        if (restHold > restMax) restMax = restHold;
                        if (splayHold > splayMax) splayMax = splayHold;
                        if (bstHold > bstMax) bstMax = bstHold;
                            
                    } else {
                        System.out.format("error: %d not in tree", query);
                        return;
                    }   
                }
                
                line = String.format("%d,%d,%d,%d\n", i*interval, restMax, splayMax, bstMax);
                maxFw.append(line);

                line = String.format("%d,%.2f,%.2f,%.2f\n", i*interval, (float) restTotal/interval, (float) splayTotal/interval, (float) bstTotal/interval);
                avgFw.append(line);

                restMax = splayMax = bstMax = 0;
                restTotal = splayTotal = bstTotal = 0;
            }
            
            maxFw.flush();
            maxFw.close();

            avgFw.flush();
            avgFw.close();
            
        } catch (IOException ex) {
            System.out.println(ex);
        }

        System.out.println("_________|_________|_________|_________|");
    }
}
