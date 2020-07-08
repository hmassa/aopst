/* Adapted from code written by Danny Sleator <sleator@cs.cmu.edu> and found at
*  https://www.link.cs.cmu.edu/splay/download/SplayTree.java 
*/

package accessoptimizedpst;

public class SplayTree implements Tree {
    private Node root;
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
	splay(key, 0);
	if ((c = key.compareTo(root.key)) == 0) {
	    //	    throw new DuplicateItemException(x.toString());	    
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

    public void remove(Comparable key) {
	Node x;
	splay(key, 0);
	if (key.compareTo(root.key) != 0) {
	    //            throw new ItemNotFoundException(x.toString());
	    return;
	}
	// Now delete the root
	if (root.left == null) {
	    root = root.right;
	} else {
	    x = root.right;
	    root = root.left;
	    splay(key, 0);
	    root.right = x;
	}
    }

    public int find(Comparable key) {
	if (root == null) {
            return 0;
        } else {
            int comparisons = splay(key, 0) + 1;
            // +1 for if (root == null) statement
            if(root.key.compareTo(key) != 0)
                return 0;
            else
                return comparisons + 1;
                // +1 for if (root.key...) statement
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

    private int splay(Comparable key, int count) {
	Node l, r, t, y;
	l = r = header;
	t = root;
	header.left = header.right = null;
	for (;;) {
            count++;
	    if (key.compareTo(t.key) < 0) {
                count++;
		if (t.left == null) break;
                // count++; restructuring
		if (key.compareTo(t.left.key) < 0) {
		    y = t.left;                            /* rotate right */
		    t.left = y.right;
		    y.right = t;
		    t = y;
                    // restructuring
		    if (t.left == null) break;
		}
		r.left = t;                                 /* link right */
		r = t;
		t = t.left;
	    } else if (key.compareTo(t.key) > 0) {
                count += 2;     // one for else if, one for if (t.right ...)
		if (t.right == null) break;
                // restructuring
		if (key.compareTo(t.right.key) > 0) {
		    y = t.right;                            /* rotate left */
		    t.right = y.left;
		    y.left = t;
		    t = y;
                    // count++; - restructuring
		    if (t.right == null) break;
		}
		l.right = t;                                /* link left */
		l = t;
		t = t.right;
	    } else {
                count++;        // for previous else if
		break;
	    }
	}
	l.right = t.left;                                   /* assemble */
	r.left = t.right;
	t.left = header.right;
	t.right = header.left;
	root = t;
        return count;
    }
}