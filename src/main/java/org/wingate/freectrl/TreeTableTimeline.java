/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.freectrl;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import org.wingate.freectrl.data.t3.AbstractNode;
import org.wingate.freectrl.data.t3.ChildNode;
import org.wingate.freectrl.data.t3.RootNode;

/**
 * An object that is similar to After Effects configurator timeline
 * @author util2
 */
public class TreeTableTimeline extends JPanel {
    
    private Color rootColor = Color.blue;
    private Color childActivatedColor = Color.green;
    private Color childDeactivatedColor = Color.red;
    
    private RootNode root = new RootNode();
    
    private int countOnY = 0;
    
    public TreeTableTimeline() {
        init();
    }
    
    private void init(){
        setDoubleBuffered(true);
        setSize(400, 200);
        
        repaint();
    }

    public List<AbstractNode> getNodes() {
        return root.getNodePath();
    }

    public void setNodes(List<AbstractNode> nodes) {
        root.setNodePath(nodes);
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);
        
        pG.setColor(Color.white);
        pG.fillRect(0, 0, getWidth(), getHeight());
        
        countOnY = 0;
        drawNodes(pG, root, 0, 0);
    }
    
    private void drawNodes(Graphics g, AbstractNode node, int x, int y){
        
        // On vérifie que le noeud n'est pas vide
        if(node == null) return;
        
        // On prépare divers vérification
        boolean isRoot = node instanceof RootNode;
        boolean isChild = node instanceof ChildNode;
        boolean isActivated = false;
        if(isChild){
            ChildNode cn = (ChildNode)node;
            isActivated = cn.isViewChildren();
        }
        
        // Taille de l'entrée carré 20x20
        // Insérer le texte à x + 22 et y + 16
        // Insérér la nouvelle ligne à y + 20
        
        // On insert un nouveau carré de couleur :
        // Bleu >> Root
        // Vert >> Child (état activé)
        // Rouge >> Child (état désactivé)
        g.setColor(isRoot ? rootColor : (isActivated ? childActivatedColor : childDeactivatedColor));
        g.fillRect(x + 2, y + 2, 16, 16);
        
        // On insert un nouveau texte :
        g.setColor(Color.black);
        g.drawString(node.getName(), x + 22, y + 16);
        
        // On a créé une entrée donc on monte en niveau
        
        
        // On cherche le nombre d'enfant
        int childrenCount = node.getNodePath().size();
        
        // Si on a des enfants 
        if(childrenCount > 0){
            
            // Pour tous les enfants trouvés, on parcourt la récursivité
            for(int i = 0; i<childrenCount; i++){
                AbstractNode n = node.getNodePath().get(i);
                drawNodes(g, n, x + 20, y + 20 + i * 20 + countOnY * 20);
            }
            countOnY = countOnY + childrenCount;
        }
        
    }
    
    public void addNode(ChildNode<?> rootChildNode){
        root.getNodePath().add(rootChildNode);
        repaint();
    }
    
    public void addNodeTo(ChildNode<?> parent, ChildNode<?> child){
        parent.getNodePath().add(child);
        repaint();
    }
    
    private void fold(AbstractNode node){
        
        repaint();
    }
    
    private void unfold(AbstractNode node){
        
        repaint();
    }

    public Color getRootColor() {
        return rootColor;
    }

    public void setRootColor(Color rootColor) {
        this.rootColor = rootColor;
    }

    public Color getChildActivatedColor() {
        return childActivatedColor;
    }

    public void setChildActivatedColor(Color childActivatedColor) {
        this.childActivatedColor = childActivatedColor;
    }

    public Color getChildDeactivatedColor() {
        return childDeactivatedColor;
    }

    public void setChildDeactivatedColor(Color childDeactivatedColor) {
        this.childDeactivatedColor = childDeactivatedColor;
    }

    public RootNode getRoot() {
        return root;
    }

    public void setRoot(RootNode root) {
        this.root = root;
    }
}
