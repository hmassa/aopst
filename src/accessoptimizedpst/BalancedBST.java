package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @authors Mayank Jaiswal at  https://www.geeksforgeeks.org/sorted-array-to-balanced-bst/
 * and Arnab Kundu at https://www.geeksforgeeks.org/iterative-searching-binary-search-tree/
 */
public class BalancedBST implements Tree {
    public Node root; 
  
    public BalancedBST(ArrayList<Comparable> points){
        if (points == null) return;
        Collections.sort(points);
        this.root = sortedArrayToBST(points, 0, points.size()-1);
    }
    
    private Node sortedArrayToBST(ArrayList<Comparable> points, int start, int end) { 
        if (start > end) { 
            return null; 
        } 
        int mid = (start + end) / 2; 
        Node node = new Node(points.get(mid)); 
        node.left = sortedArrayToBST(points, start, mid - 1); 
        node.right = sortedArrayToBST(points, mid + 1, end); 
          
        return node; 
    } 
  
    @Override
    public int find(Comparable key) {
        int count = 0;
        Node node = this.root;
        
        while (node != null) { 
            count += 2;  // one for while, one for if
            if (key.compareTo(node.key) > 0) {
                node = node.right; 
            } else if (key.compareTo(node.key) < 0) {
                count++;
                node = node.left;
                
            } else {
                count++;
                return count; 
            }
        } 
        return 0; 
    }
    
    public void preOrder(Node node) { 
        if (node == null) { 
            return; 
        } 
        System.out.print(node.key + ", "); 
        preOrder(node.left); 
        preOrder(node.right); 
    } 
}
