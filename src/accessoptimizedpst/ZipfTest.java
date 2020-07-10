package accessoptimizedpst;

import java.util.Collections;

public class ZipfTest extends Test{
    private int size = 100;
    private double skew = 1.07d;

    @Override
    void generateKeys() {
        for (int i = 1; i < size; i++){
            keys.add(i);
        }
    }

    @Override
    String getSheetName() {
        return "Zipf";
    }

    @Override
    void generateQueries() {
        int baseNum = 500;
        for (int i = 1; i <= size; i++){
            int rankNum = (int)Math.round(baseNum/(Math.pow(i, skew)));
            for (int j = 0; j < rankNum; j++){
                queries.add(i);
            }
        }
        
        Collections.shuffle(queries);
    }

    @Override
    String getString(Comparable x) {
        Integer i = (Integer) x;
        return Integer.toString(i);
    }
}