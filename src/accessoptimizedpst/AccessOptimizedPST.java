package accessoptimizedpst;

import java.io.IOException;
/**
 * @author flipp
 */
public class AccessOptimizedPST{
    public static void main(String[] args) throws IOException{
        Test test = new ZipfTest();
        
        test.generateKeys();
        test.generateTrees();

        test.openExcel();
        test.generateQueries();
        test.searchAndWrite();
    }
}
