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

public class PointerPSTNode {
    Comparable px;
    int py;
    Comparable qx;
    int qy;
    boolean validP;
    boolean duplQ;
    PointerPSTNode left;
    PointerPSTNode right;
    PointerPSTNode parent;
}