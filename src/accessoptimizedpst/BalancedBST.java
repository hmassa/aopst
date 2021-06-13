package accessoptimizedpst;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @authors Mayank Jaiswal at  https://www.geeksforgeeks.org/sorted-array-to-balanced-bst/
 * and Arnab Kundu at https://www.geeksforgeeks.org/iterative-searching-binary-search-tree/
 */
public class BalancedBST implements Tree {
    public Node root; 
  
    public BalancedBST(ArrayList<Integer> points){
        if (points == null) return;
//        Collections.sort(points);
        this.root = sortedArrayToBST(points, 0, points.size()-1);
    }
    
    private Node sortedArrayToBST(ArrayList<Integer> points, int start, int end) { 
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
            int diff = key.compareTo(node.key);
            count++;
            if (diff > 0) {
                node = node.right; 
            } else if (diff < 0) {
                node = node.left;   
            } else {
                return count; 
            }
        } 
        return count;
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
