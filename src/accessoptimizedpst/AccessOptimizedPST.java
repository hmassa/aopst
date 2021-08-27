package accessoptimizedpst;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
/**
 * @author flipp
 */
public class AccessOptimizedPST{
    public static void main(String[] args) throws IOException{
        Test test = new SplayWorstCaseTest();        //  new ParamTest();
        
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
        
//        System.out.println("*Averaged over " + NumberFormat.getInstance().format(test.numQueries) + " queries");
    }
}
