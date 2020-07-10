package accessoptimizedpst;

import java.util.Collections;

/**
 * @author flipp
 */
public class UniformDistTest extends Test { 
    @Override
    void generateKeys() {
        for (int i = 0; i < 1000; i++){
            keys.add(i);
            queries.add(i);
            queries.add(i);
            queries.add(i);
        }
        Collections.shuffle(keys);
    }

    @Override
    void generateQueries() {
        Collections.shuffle(queries);
    }

    @Override
    String getString(Comparable x) {
        Integer i = (Integer) x;
        return Integer.toString(i);
    }

    @Override
    String getSheetName() {
        return "Uniform Dist";
    }
}
