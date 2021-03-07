/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.freectrl.data.t3;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class for the common set-up of a Node
 * @author util2
 */
public abstract class AbstractNode implements Node {
    protected String name = "Name";
    protected List<AbstractNode> nodePath = new ArrayList<>();
    protected List<Box> boxes = new ArrayList<>();
    protected List<State> states = new ArrayList<>();
    protected boolean viewChildren = false;

    public AbstractNode() {
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
     * Get the path until the required Node
     * @return A path
     */
    public List<AbstractNode> getNodePath() {
        return nodePath;
    }

    /**
     * Set the path until the required Node
     * @param nodePath A path
     */
    public void setNodePath(List<AbstractNode> nodePath) {
        this.nodePath = nodePath;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public boolean isViewChildren() {
        return viewChildren;
    }

    public void setViewChildren(boolean viewChildren) {
        this.viewChildren = viewChildren;
    }
}
