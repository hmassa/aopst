package accessoptimizedpst;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class BstAveragesTest  extends Test {
    private ArrayList<Integer> keys;
    private int[] queryKeys;

    @Override
    void generateTrees() {
        keys = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            keys.add(i);
        }

        bst = new BalancedBST(keys);
    }

    @Override
    void generateQueries() {
        Collections.shuffle(keys);

        queryKeys = new int[64];
        for (int i = 0; i < 64; i++){
            queryKeys[i] = keys.get(i);
        }

        numQueries = 10000;
    }

    @Override
    void setName() {
        testName = "BST Average Comparisons";
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
        return ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
    }

    @Override
    public void searchAndWrite() {
        int bstHold;
        long bstTotal = 0;

        File bstAvgFile;
        FileWriter bstFw;

        int query;
        int random;

        float averages[] = new float[10];
        float currentAvg;
        String line;

        try {
            bstAvgFile = new File("./Results/bstAvg.txt");
            bstFw = new FileWriter(bstAvgFile);
            bstFw.write("p,average\n");

            for (int k =0; k < 100; k++) {
                generateQueries();
                System.out.println(queryKeys[0]);
                for (int j = 0; j < 10; j++) {
                    double p = 0.5 + j*0.05;
                    generateTrees();
                    for (int i = 0; i < numQueries; i++) {
                        random = ThreadLocalRandom.current().nextInt(0, 100);
                        if (random > p * 100) {
                            query = ThreadLocalRandom.current().nextInt(1, numKeys);
                        } else {
                            query = queryKeys[tossCoin()];
                        }

                        bstHold = bst.find(query);

                        if (bstHold > 0) {
                            bstTotal += bstHold;
                        } else {
                            System.out.format("error: %d not in tree", query);
                            return;
                        }
                    }

                    currentAvg = (float) bstTotal / numQueries;
                    averages[j] = (averages[j] + currentAvg)/2;

                    bstTotal = 0;
                }
            }

            for (int i = 0; i < 10; i++) {
                line = String.format("%.2f,%.4f\n", i*0.05 + 0.5, averages[i]);
                bstFw.append(line);
            }

            bstFw.flush();
            bstFw.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("_________|_________|_________|_________|");
    }
}

