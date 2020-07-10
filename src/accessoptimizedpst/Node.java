package accessoptimizedpst;

class Node
{
    Comparable key;
    Node left;
    Node right;
    
    Node(Comparable theKey) {
        key = theKey;
        left = right = null;
    }
}