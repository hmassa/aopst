package accessoptimizedpst;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author flipp
 */
public abstract class Test {
    protected SplayTree splayTree = new SplayTree();
    protected BalancedBST bst;
    protected StaticAOPST aopst;
    
    protected ArrayList<Comparable> queries = new ArrayList<>();
    protected String testName;
    protected int numKeys;
    protected int numQueries;
    
    public void createChart() {
        setName();
        System.out.println(testName);
        System.out.println("Average number of comparisons per search: ");
        System.out.println(" DB Size |   BST   |  Splay  |  AOPST  |");
        System.out.println("_________|_________|_________|_________|");
    }
    
    public void searchAndWrite() {
        int bstTotal = 0;
        int splayTotal = 0;
        int aopstTotal = 0;
        for (Comparable query : queries) {
            aopstTotal += aopst.find(query);
            bstTotal += bst.find(query);
            splayTotal += splayTree.find(query);
        }
        float bstAvg = (float)bstTotal/(queries.size());
        float splayAvg = (float)splayTotal/(queries.size());
        float aopstAvg = (float)aopstTotal/(queries.size());
        
        String dbSize = Integer.toString(numKeys/1000) + "k";
        System.out.printf("%-9s|%-9.4f|%-9.4f|%-9.4f|\n", dbSize, bstAvg, splayAvg, aopstAvg);
        System.out.println("_________|_________|_________|_________|");
    }
    
//    public void setKeySize(int keys) {
//        this.numKeys = keys;
//    }
    
    abstract void generateQueries();
    abstract void generateTrees();
    abstract void setName();
}
