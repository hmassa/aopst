package accessoptimizedpst;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class AveragesTest  extends Test {
    private ArrayList<Integer> keys;
    private int[] queryKeys;

    @Override
    void generateTrees() {
        keys = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            keys.add(i);
        }

        bst = new BalancedBST(keys);
        rest = new RestructuringAOPST(keys);
    }

    @Override
    void generateQueries() {
//        Collections.shuffle(keys);

        queryKeys = new int[64];
        for (int i = 0; i < 64; i++){
            queryKeys[i] = keys.get(i);
        }

        numQueries = 1000000;
    }

    @Override
    void setName() {
        testName = "BST and AAPST Average Comparisons";
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
        int bstHold, aapstHold;
        long bstTotal = 0, aapstTotal = 0;

        File bstAvgFile;
        FileWriter bstFw;

        File aapstAvgFile;
        FileWriter aapstFw;

        int query;
        int random;

        String line;

        try {
            bstAvgFile = new File("./Results/bstAvg.txt");
            bstFw = new FileWriter(bstAvgFile);
            bstFw.write("p,average\n");

            aapstAvgFile = new File("./Results/aapstAvg.txt");
            aapstFw = new FileWriter(aapstAvgFile);
            aapstFw.write("p,average\n");

            for (double p = 0.95; p > 0.49; p -= 0.05) {
                generateTrees();
                for(int i = 0; i < numQueries; i++) {
                    random = ThreadLocalRandom.current().nextInt(0, 100);
                    if (random > p * 100) {
                        query = ThreadLocalRandom.current().nextInt(1, numKeys);
                    } else {
                        query = queryKeys[tossCoin()];
                    }

                    bstHold = bst.find(query);
                    aapstHold = rest.find(query);

                    if (bstHold > 0) {
                        bstTotal += bstHold;
                        aapstTotal += aapstHold;
                    } else {
                        System.out.format("error: %d not in tree", query);
                        return;
                    }
                }

                line = String.format("%.2f,%.4f\n", p, (float) bstTotal/numQueries);
                bstFw.append(line);

                line = String.format("%.2f,%.4f\n", p, (float) aapstTotal/numQueries);
                aapstFw.append(line);

                bstTotal = 0;
                aapstTotal = 0;
            }

            bstFw.flush();
            bstFw.close();

            aapstFw.flush();
            aapstFw.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("_________|_________|_________|_________|");
    }
}

