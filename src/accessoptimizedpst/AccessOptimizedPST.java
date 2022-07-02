package accessoptimizedpst;

import java.io.IOException;
import java.text.NumberFormat;
/**
 * @author flipp
 */
public class AccessOptimizedPST{
    public static void main(String[] args) throws IOException{
        Test test = new SplayWorstCase();

        test.createChart();

        test.setKeySize(100000);
        test.run();
    }
}
