package accessoptimizedpst;

import java.io.IOException;
/**
 * @author flipp
 */
public class AccessOptimizedPST{
    public static void main(String[] args) throws IOException{
        Test test = new WordTest();
        
        test.generateQueries();
        test.generateTrees();

        test.openExcel();
        test.searchAndWrite();
        
    }
}
