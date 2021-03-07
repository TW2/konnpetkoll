/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.freectrl.data.t3;

/**
 * An abstract box that accepts any object class
 * @param <T> Object class type
 * @author util2
 */
public abstract class AbstractBox<T> implements Box {
    
    protected String name = "Name";
    protected T property = null;
    protected boolean activate = false;

    public AbstractBox() {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the object property
     * @return An object property
     */
    public T getProperty() {
        return property;
    }

    /**
     * Set the object property
     * @param property An object corresponding to the property
     */
    public void setProperty(T property) {
        this.property = property;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }
}
