/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accessoptimizedpst;

/**
 *
 * @author flipp
 */
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