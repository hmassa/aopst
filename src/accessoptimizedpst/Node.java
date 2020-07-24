package accessoptimizedpst;

public class Node
{
    Comparable key;
    Node left;
    Node right;
    
    Node(Comparable theKey) {
        key = theKey;
        left = right = null;
    }
}