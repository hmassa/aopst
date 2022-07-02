package accessoptimizedpst;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class OverSixtyCount extends Test {
    private ArrayList<Integer> keys;
    private int[] queryKeys;

    @Override
    void generateTrees() {
        keys = new ArrayList<>();
        for (int i = 1; i <= numKeys; i++) {
            keys.add(i);
        }

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
        testName = "Splay Over 60 Comparisons Count";
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
        int splayHold;
        int splayCount = 0;

        File countFile;
        FileWriter countFw;

        int query;
        int random;

        String line;

        try {
            countFile = new File("./Results/count.txt");
            countFw = new FileWriter(countFile);
            countFw.write("p,Over 60 Count\n");

            for (double p = 0.5; p < 1; p += 0.05) {
                for(int i = 0; i < numQueries; i++) {
                    random = ThreadLocalRandom.current().nextInt(0, 100);
                    if (random > p * 100) {
                        query = ThreadLocalRandom.current().nextInt(1, numKeys);
                    } else {
                        query = queryKeys[tossCoin()];
                    }

                    splayHold = splayTree.find(query);

                    if (splayHold > 0) {
                        if (splayHold >= 60) {
                            splayCount++;
                        }
                    } else {
                        System.out.format("error: %d not in tree", query);
                        return;
                    }
                }

                line = String.format("%.2f,%d\n", p, splayCount);
                countFw.append(line);

                splayCount = 0;
            }

            countFw.flush();
            countFw.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }

        System.out.println("_________|_________|_________|_________|");
    }
}
