package accessoptimizedpst;

import java.util.ArrayList;

public class DemoTest extends Test {
    private ArrayList<Integer> keys;
    private ArrayList<Comparable> queries;

    @Override
    void generateQueries() {
        queries = new ArrayList<>(6);
        queries.add(6);
        queries.add(5);
        queries.add(5);
        queries.add(7);
    }

    @Override
    void generateTrees() {
        keys = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            keys.add(i);
        }

        rest = new RestructuringAOPST(keys);
    }

    @Override
    void searchAndWrite() {
        for (int i = 0; i < 4; i++) {
            Comparable query = (Comparable) queries.get(i);
            int count = rest.find(query);
            System.out.println("Query: " + query + "  Count: " + count + "\n");
        }
    }

    @Override
    void setName() {
        testName = "Demo Test";
    }
}
