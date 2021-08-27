// Adapted from code written by Simon Pratt and found at 
// https://github.com/hmassa/PrioritySearchTree

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
import java.util.*;

public class RestructuringAOPST implements Tree{
    public PointerPSTNode root;

    int count = 0;
    
    public RestructuringAOPST(ArrayList<Integer> xVals) {
	if(xVals.isEmpty()) return;
        Collections.sort(xVals);
	this.root = buildTree(xVals, 0, xVals.size(), null);
    }

    private PointerPSTNode buildTree(ArrayList<Integer> xVals, int minInd, int maxInd, PointerPSTNode parent) {
        if (minInd >= maxInd){
            return null;
        }
        
        int middle = (int) floor((maxInd + minInd)/2);
        PointerPSTNode node = new PointerPSTNode();
        node.parent = parent;
        node.qx = xVals.get(middle);
        node.qy = 0;
        node.px = node.py = -1;
        node.validP = false;
        
        node.left = buildTree(xVals, minInd, middle, node);
        node.right = buildTree(xVals, middle + 1, maxInd, node);
        
        return node;       
    }
    
    @Override
    public int find(Comparable xVal){
        count = 0;
        PointerPSTNode node = this.root;
        int diff;
	
        while (node != null){
            if (node.validP) {
                diff = compareX(xVal, node.px);
                if (diff == 0) {
                    increment(node, true);
                    return count;
                }
            }
            
            diff = compareX(xVal, node.qx);
            if (diff == 0) {
                increment(node, false);
                return count;
            } else if (diff > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return -1;
    }
    
    private void increment(PointerPSTNode node, boolean p) {
        Comparable xHold;
        int yHold;
        
        if (p) {
            node.py++;

            if (node.parent == null || compareY(node.py, node.parent.py) <= 0) {
                return;
            }
            
            xHold = node.px;
            yHold = node.py;
            deleteP(node);
        } else {
            if (node.parent == null) {
                return;
            }
            
            if (node.parent.validP && compareY(node.qy, node.parent.py) <= 0) {
                return;
            }
            
            node.qy++;
            xHold = node.qx;
            yHold = node.qy;    
        } 
        
        insertP(xHold, yHold, node);
    }
    
    private void deleteP(PointerPSTNode node){
        while (true) {
            if (node.right == null && node.left == null) {
                clearP(node);
                break;
            } else if (node.right == null) {
                if (node.left.validP) {
                    node.px = node.left.px;
                    node.py = node.left.py;
                    node = node.left;
                } else {
                    clearP(node);
                    break;
                }
            } else if (node.left == null) {
                if (node.right.validP) {
                    node.px = node.right.px;
                    node.py = node.right.py;
                    node = node.right;
                } else {
                    clearP(node);
                    break;
                }
            } else {
                if (node.right.validP && node.left.validP){
                    int diff = compareY(node.right.py, node.left.py);
                    if (diff > 0) {
                        node.px = node.right.px;
                        node.py = node.right.py;
                        node = node.right;
                    } else {
                        node.px = node.left.px;
                        node.py = node.left.py;
                        node = node.left;
                    }
                } else if (node.right.validP) {
                    node.px = node.right.px;
                    node.py = node.right.py;
                    node = node.right;
                } else if (node.left.validP) {
                    node.px = node.left.px;
                    node.py = node.left.py;
                    node = node.left;
                } else {
                    clearP(node);
                    break;
                }
            }
        }
    }
    
    private void insertP(Comparable xVal, int yVal, PointerPSTNode startNode){
        PointerPSTNode current = startNode;
        
        while (current.parent != null && !current.parent.validP) {
            current = current.parent;
        }

        while (current.parent != null && compareY(yVal, current.parent.py) > 0) {
            current = current.parent;
        }

        if (current.parent == null) {
            current.px = xVal;
            current.py = yVal;
            current.validP = true;
            return;
        }
        
        floatDown(current);
        current.px = xVal;
        current.py = yVal;
        current.validP = true;
    }
    
    private void floatDown(PointerPSTNode current) {
        PointerPSTNode child;
        
        if (!current.validP) {
            return;
        }
        
        int xDiff = compareX(current.px, current.qx);
        if (xDiff > 0) {
            child = current.right;
        } else if (xDiff < 0){
            child = current.left;
        } else {
            return;
        }
        
        floatDown(child);
        child.px = current.px;
        child.py = current.py;
        child.validP = true;
    }
    
    private int compareX(Comparable a, Comparable b) {
        count++;
        return a.compareTo(b);
    }
    
    private int compareY(int a, int b) {
        count++;
        return a - b;
    }
    
    private void clearP(PointerPSTNode node) {
        node.px = -1;
        node.py = -1;
        node.validP = false;
    }

}