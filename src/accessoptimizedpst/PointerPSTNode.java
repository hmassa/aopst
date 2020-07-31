/******************************************************************************
*                       Copyright (c) 2011 - 2012 by                          *
*                               Simon Pratt                                   *
*                         (All rights reserved)                               *
*******************************************************************************
*                                                                             *
* FILE:    PointerPSTNode.java                                                *
*                                                                             *
* MODULE:  Priority Search Tree                                               *
*                                                                             *
* NOTES:   See README for more information.                                   *
*                                                                             *
*          See LICENSE for license information.                               *
*                                                                             *
******************************************************************************/
package accessoptimizedpst;

public class PointerPSTNode implements Comparable<PointerPSTNode>{
    private Comparable x;
    private double y;
    private PointerPSTNode leftChild, rightChild, parent;
    private Comparable maxLeft;

    public PointerPSTNode(Comparable x, double y) {
	this.x = x;
        this.y = y;
    }
    
    public Comparable getX() { return x; }
    public double getY() { return y; }
    public Comparable getMaxLeft() { return maxLeft; }
    public String getPoint() { return "(" + x +", " + y +")"; }
    public PointerPSTNode getLeftChild() { return leftChild; }
    public PointerPSTNode getRightChild() { return rightChild; }
    public PointerPSTNode getParent() { return parent; }
    
    public void setLeftChild(PointerPSTNode p) {
	this.leftChild = p;
    }
    public void setRightChild(PointerPSTNode p) {
	this.rightChild = p;
    }
    public void setParent(PointerPSTNode p) {
        this.parent = p;
    }
    public void incY() {
        this.y += 1;
    }
    public void setMaxLeft(Comparable max){
        this.maxLeft = max;
    }
    
    public void clear() {
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
        this.maxLeft = null;
    }

    @Override
    public int compareTo(PointerPSTNode p) {
	if(this.y < p.getY()) return 1;
	else if(this.y > p.getY()) return -1;
	return 0;
    }
}