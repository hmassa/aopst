package accessoptimizedpst;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;


public class ParetoTest extends Test {
    @Override
    void generateKeys() {
        for (int i = 0; i < 1000; i++)
            keys.add(i);
        
        Collections.shuffle(keys);
    }

    @Override
    String getSheetName() {
        return "Pareto";
    }

    @Override
    void generateQueries() {
        for (int j = 0; j < 4000; j++)
            queries.add(ThreadLocalRandom.current().nextInt(0, 200));
        
        for (int k = 0; k < 1000; k++)
            queries.add(ThreadLocalRandom.current().nextInt(200, 1000));
        
        Collections.shuffle(queries);
    }

    @Override
    String getString(Comparable x) {
        Integer i = (Integer) x;
        return Integer.toString(i);
    }
}
