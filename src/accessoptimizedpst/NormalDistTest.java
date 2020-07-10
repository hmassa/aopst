package accessoptimizedpst;

import java.util.Collections;
import java.util.Random;
/**
 * @author flipp
 */
public class NormalDistTest extends Test{
    @Override
    void generateKeys() {
        for (int i = 0; i < 1000; i++){
            keys.add(i);
        }
        Collections.shuffle(keys);
    }

    @Override
    void generateQueries() {
        Random ran = new Random();
        for (int j = 0; j < 3000; j++){
            double val = ran.nextGaussian()*100 + 500;
            queries.add((int)Math.round(val));
        }
    }

    @Override
    String getString(Comparable x) {
        Integer i = (Integer) x;
        return Integer.toString(i);
    }

    @Override
    String getSheetName() {
        return "Normal Dist";
    }
}
