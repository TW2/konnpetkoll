/*
 * Copyright (C) 2024 util2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wingate.konnpetkoll.swing;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author util2
 */
public class ColorViewer extends JPanel {

    private Color showColor;
    /**
     * Creates new form ColorViewer
     */
    public ColorViewer() {
        initComponents();
        showColor = Color.red;
    }

    public Color getShowColor() {
        return showColor;
    }

    public void setShowColor(Color showColor) {
        this.showColor = showColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.lightGray);
        
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int xa, ya = y, yb = y + 10;
        boolean pair = true;
        
        xa = x;        
        while(xa < getWidth()){            
            while(ya >= -10){
                g.fillRect(xa, ya - 10, 10, 10);
                ya -= 20;
            }
            xa += 10;
            ya = pair ? y - 10 : y;
            pair = !pair;
        }
        
        pair = true;
        xa = x;
        while(xa < getWidth()){            
            while(yb <= getHeight() + 10){
                g.fillRect(xa, yb, 10, 10);
                yb += 20;
            }
            xa += 10;
            yb = pair ? y : y + 10;
            pair = !pair;
        }
        
        int cr = showColor.getRed();
        int cg = showColor.getGreen();
        int cb = showColor.getBlue();        

        g.setColor(new Color(cr, cg, cb));
        g.fillRect(0, 0, getWidth() / 2, getHeight());
        
        g.setColor(showColor);
        g.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}