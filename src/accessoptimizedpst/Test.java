package accessoptimizedpst;

import java.util.ArrayList;


/**
 * @author flipp
 */
public abstract class Test {
    protected SplayTree splayTree;
    protected RestructuringAOPST rest;
    protected BalancedBST bst;
    
    protected ArrayList<Comparable> queries;
    protected String testName;
    protected int numKeys;
    protected int numQueries;
    
    public void createChart() {
        setName();
        System.out.println(testName);
        System.out.println("Average number of comparisons per search: ");
        System.out.println(" DB Size |  Splay  | AAPST   | BST  |");
        System.out.println("_________|_________|_________|_________|");
    }
    
    public void setKeySize(int keys) {
        this.numKeys = keys;
    }
    
    public void run() {
        generateQueries();
        generateTrees();
        searchAndWrite();
    }
    
    abstract void generateQueries();
    abstract void generateTrees();
    abstract void searchAndWrite();
    abstract void setName();
}
