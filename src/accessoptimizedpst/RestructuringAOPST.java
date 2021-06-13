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
        node.duplQ = false;
        
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
                diff = xVal.compareTo(node.px);
                count++;
                if (diff == 0) {
                    increment(node, true);
                    return count;
                }
            }
            
            diff = xVal.compareTo(node.qx);
            count++;

            if (diff == 0) {
                increment(node, false);
                return count;
            } else if (diff > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return count;
    }
    
    private void increment(PointerPSTNode node, boolean p) {
        Comparable xHold;
        int yHold;
        Comparable xCurr;
        int yCurr;
        
        if (p) {
            node.py++;
            PointerPSTNode hold_node = node;
            
            while(hold_node != null) {
                int diff = node.px.compareTo(hold_node.qx);
                count++;
                
                if (diff > 0){
                    hold_node = hold_node.right;
                } else if (diff < 0) {
                    hold_node = hold_node.left;
                } else {
                    hold_node.qy++;
                    break;
                }
            }            

            hold_node = node;
            int diff = node.py - node.parent.py;
            count++;
            
            while (diff > 0) {
                node = node.parent;
                if (node.parent == null) {
                    break;
                }
                
                diff = node.py - node.parent.py;
                count++;
            }
            
            xCurr = hold_node.px;
            yCurr = hold_node.py;
            
            while(true) {
                if (!node.validP) {
                    node.px = xCurr;
                    node.py = yCurr;
                    break;
                }
                
                xHold = node.px;
                yHold = node.py;
                node.px = xCurr;
                node.py = yCurr;
                xCurr = xHold;
                yCurr = yHold;

                diff = xCurr.compareTo(node.qx);
                if (diff == 0) {
                    break;
                } else if (diff > 0) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            
        } else {
            node.qy++;
            xCurr = node.qx;
            yCurr = node.qy;
            node = this.root;
            
            while (true) {
                count++;
                int diff = xCurr.compareTo(node.qx);
                if (diff == 0) {
                    break;
                }
                
                if (!node.validP) {
                    node.py = yCurr;
                    node.px = xCurr;
                    node.validP = true;
                    break;
                } else if (yCurr <= node.py) {
                    count++;
                    if (diff > 0) {
                        node = node.right;
                    } else {
                        node = node.left;
                    }
                } else {
                    count++;
                    
                    xHold = node.px;
                    yHold = node.py;
                    node.px = xCurr;
                    node.py = yCurr;
                    
                    xCurr = xHold;
                    yCurr = yHold;
                    
                    diff = xCurr.compareTo(node.qx);
                    count++;
                    
                    if (diff == 0) {
                        break;
                    } else if (diff > 0) {
                        node = node.right;
                    } else {
                        node = node.left;
                    }
                }
            } 
        }
    }

}