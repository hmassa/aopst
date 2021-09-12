package accessoptimizedpst;

import java.io.IOException;
import java.text.NumberFormat;
/**
 * @author flipp
 */
public class AccessOptimizedPST{
    public static void main(String[] args) throws IOException{
        Test test = new ParamTest();
        
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
        
        NumberFormat nf = NumberFormat.getInstance();
        System.out.println("*Averaged over " + nf.format(test.numQueries) + " queries");    

    }
}
