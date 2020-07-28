package accessoptimizedpst;

import java.io.IOException;
import java.util.ArrayList;
/**
 * @author flipp
 */
public class AccessOptimizedPST{
    public static void main(String[] args) throws IOException{
        Test test = new CoinTossTest();
        
        test.createChart();
        test.setKeySize(10000);
        test.generateQueries();
        test.generateTrees();
        test.searchAndWrite();
        
        test.setKeySize(100000);
        test.generateQueries();
        test.generateTrees();
        test.searchAndWrite();
        
        test.setKeySize(1000000);
        test.generateQueries();
        test.generateTrees();
        test.searchAndWrite();
    }
}
