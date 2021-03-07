/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.freectrl.data.t3;

/**
 * Represent a normal node
 * @param <T> Type of the object to link
 * @author util2
 */
public class ChildNode<T> extends AbstractNode {
    
    private T object = null;
        
    public ChildNode() {
        name = "Node";
    }

    public ChildNode(T object) {
        name = "Node";
        this.object = object;
    }

    /**
     * Get the object
     * @return A converted object
     */
    public T getObject() {
        return object;
    }

    /**
     * Set the object
     * @param object A source object
     */
    public void setObject(T object) {
        this.object = object;
    }
    
}
