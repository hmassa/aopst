/* Adapted from code written by Danny Sleator <sleator@cs.cmu.edu> and found at
*  https://www.link.cs.cmu.edu/splay/download/SplayTree.java 
*/

package accessoptimizedpst;

public class SplayTree implements Tree {
    private Node root;
    private int count;
    
    public SplayTree() {
        root = null;
    }

    public void insert(Comparable key) {
	Node n;
	int c;
	if (root == null) {
	    root = new Node(key);
	    return;
	}
	splay(key);
        
        c = compare(key, root.key);
	if (c == 0) {    
	    return;
	}
	n = new Node(key);
	if (c < 0) {
	    n.left = root.left;
	    n.right = root;
	    root.left = null;
	} else {
	    n.right = root.right;
	    n.left = root;
	    root.right = null;
	}
	root = n;
    }

    @Override
    public int find(Comparable key) {
        count = 0;
        
	if (root == null) {
            return 0;
        } else {
            return splay(key); 
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    private static Node header = new Node(null); // For splay
    
    /**
     * Internal method to perform a top-down splay.
     * 
     *   splay(key) does the splay operation on the given key.
     *   If key is in the tree, then the BinaryNode containing
     *   that key becomes the root.  If key is not in the tree,
     *   then after the splay, key.root is either the greatest key
     *   < key in the tree, or the lest key > key in the tree.
     *
     *   This means, among other things, that if you splay with
     *   a key that's larger than any in the tree, the rightmost
     *   node of the tree becomes the root.  This property is used
     *   in the delete() method.
     */

    private int splay(Comparable key) {
	Node l, r, t, y;
	l = r = header;
	t = root;
	header.left = header.right = null;
	for (;;) {
            int diff = compare(key, t.key);
	    if (diff < 0) {
		if (t.left == null) break;
		if (compare(key,t.left.key) < 0) {
		    y = t.left;                            /* rotate right */
		    t.left = y.right;
		    y.right = t;
		    t = y;
                    if (t.left == null) break;
		}
		r.left = t;                                 /* link right */
		r = t;
		t = t.left;
	    } else if (diff > 0) {
		if (t.right == null) break;
		if (compare(key, t.right.key) > 0) {
		    y = t.right;                            /* rotate left */
		    t.right = y.left;
		    y.left = t;
		    t = y;
                    if (t.right == null) break;
		}
		l.right = t;                                /* link left */
		l = t;
		t = t.right;
	    } else {
		break;
	    }
	}
	l.right = t.left;                                   /* assemble */
	r.left = t.right;
	t.left = header.right;
	t.right = header.left;
	root = t;
        
        if (t.key.compareTo(key) == 0)
            return count;
        else return -1;
    }
    
    private int compare(Comparable a, Comparable b) {
        count++;
        return a.compareTo(b);
    }
}