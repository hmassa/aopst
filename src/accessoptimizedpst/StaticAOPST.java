/******************************************************************************
*                       Copyright (c) 2011 - 2012 by                          *
*                               Simon Pratt                                   *
*                         (All rights reserved)                               *
*******************************************************************************
*                                                                             *
* FILE:    PointerPST.java                                                    *
*                                                                             *
* MODULE:  Priority Search Tree                                               *
*                                                                             *
* NOTES:   A priority search tree is a tree data structure which stores       *
*          a set of coordinates in sorted order.  The root element of         *
*          the tree is the point with highest Y value.  The rest of           *
*          the points are divided into two sets, one set having               *
*          smaller x values than the median, the other having higher.         *
*                                                                             *
*          See README for more information.                                   *
*                                                                             *
*          See LICENSE for license information.                               *
*                                                                             *
******************************************************************************/
package accessoptimizedpst;

import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Simon Pratt at https://github.com/hmassa/PrioritySearchTree
 */
public class StaticAOPST implements Tree {
    public PointerPSTNode root;
    
    public StaticAOPST(ArrayList<PointerPSTNode> points) {
	if(points == null) return;
	Collections.sort(points); // Sort by y-coordinate in decreasing order
	this.root = buildTree(points);
    }
/******************************************************************************
* Given a list of valid points P ordered by y-coordinate in decreasing        *
* order, determines a median which bisects the remaining points, then         *
* builds:                                                                     *
*                                                                             *
*   root: point with lowest y-value                                           *
*   left child:  {p ∈ (P - root) | p.x <= medianX}                            *
*   right child: {p ∈ (P - root) | p.x >  medianX}                            *
*                                                                             *
* Note: points are also assumed to have distinct coordinates, i.e. no         *
*       two points have the same x coordinate and no two points have          *
*       the same y coordinate.                                                *
*                                                                             *
*       While this may seem unrealistic, we can convert any indistinct        *
*       coordinates by replacing all real coordinates with distinct           *
*       coordinates from the composite-number space without any loss          *
*       of generality.  See: Computational Geometry: Applications and         *
*       Algorithms, de Berg et al.  Section 5.5.                              *
*                                                                             *
******************************************************************************/
    // Assumes all points are valid (e.g. not null)
    private PointerPSTNode buildTree(ArrayList<PointerPSTNode> nodes) {
	if(nodes == null || nodes.size() < 1) return null;
        if (nodes.size() == 1){
            return nodes.get(0);
        }
	// Find point with highest Y value
	PointerPSTNode rootNode = nodes.remove(0);
	// Find median X value
        int numNodes = nodes.size();
        ArrayList<Comparable> xPoints = new ArrayList<>();
        for(PointerPSTNode node : nodes){
	    xPoints.add(node.getX());
        }
        Collections.sort(xPoints);
	Comparable medianX = xPoints.get((int)floor((numNodes-1)/2));
        rootNode.setMaxLeft(medianX);
	// Make upper and lower point array
	ArrayList<PointerPSTNode> upperPoints = new ArrayList<>(); 
	ArrayList<PointerPSTNode> lowerPoints = new ArrayList<>();
	for(PointerPSTNode node : nodes) {
	    if(node.getX().compareTo(medianX) <= 0) lowerPoints.add(node);
	    else upperPoints.add(node);
	}
	// Make tree
        PointerPSTNode hold = null;
	if(lowerPoints.size() > 0){
            hold = buildTree(lowerPoints);
	    rootNode.setLeftChild(hold);
            if (hold != null)
                hold.setParent(rootNode);
        }
	if(upperPoints.size() > 0){
            hold = buildTree(upperPoints);
	    rootNode.setRightChild(hold);
            if (hold != null)
                hold.setParent(rootNode);
        }
	return rootNode;
    }
    
    @Override
    public int find(Comparable xVal){
        int count = 0;
	return aopstSearch(xVal, root, count);
    }
    private int aopstSearch(Comparable xVal, PointerPSTNode node, int count){
	if(node == null) {
            return count;
        }
        int diff = node.getX().compareTo(xVal);
        count++;
	if(diff == 0) { 
            return count;
	} else {
            PointerPSTNode leftChild = node.getLeftChild();
            if(leftChild != null) {
                int direction = node.getMaxLeft().compareTo(xVal);
                count++;
                if(direction >= 0)
                    return aopstSearch(xVal, leftChild, count); 
                else 
                    return aopstSearch(xVal, node.getRightChild(), count);
            }
            return count;
        }
    }

// Utility Functions

    public void printTree(){
        PointerPSTNode node = root;
        printTree(node);
    }
    
    private void printTree(PointerPSTNode node){
        if (node != null) {
            printTree(node.getLeftChild());
            System.out.println(node.getPoint());
            printTree(node.getRightChild());  
        }
    }
}
