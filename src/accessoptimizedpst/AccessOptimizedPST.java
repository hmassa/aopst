package accessoptimizedpst;

import java.io.IOException;
/**
 * @author flipp
 */
public class AccessOptimizedPST{
    public static void main(String[] args) throws IOException{
        Test test = new CoinTossTest();
        
        test.createChart();
        
        test.setKeySize(1000);
        test.run();
        
        test.setKeySize(10000);
        test.run();
        
        test.setKeySize(100000);
        test.run();
        
        test.setKeySize(1000000);
        test.run();
        
        System.out.println("");
        System.out.println("*Averaged over 1,000,000,000 queries");    

    }
}
