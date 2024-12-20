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
package org.wingate.konnpetkoll;

import javax.swing.JScrollPane;
import org.wingate.konnpetkoll.swing.ColorViewer;
import org.wingate.konnpetkoll.swing.KKPrTable;
import org.wingate.konnpetkoll.swing.KKPrTable.KKCheckBox;
import org.wingate.konnpetkoll.swing.KKPrTable.KKList;
import org.wingate.konnpetkoll.swing.KKPrTable.KKWithDialog;
import org.wingate.konnpetkoll.swing.Waveform;
import org.wingate.konnpetkoll.swing.Spectrogram;
import org.wingate.konnpetkoll.swing.PlaceholderTextField;

/**
 *
 * @author util2
 */
public class MainFrame extends javax.swing.JFrame {
    
    private final KKPrTable table = new KKPrTable();
    private final JScrollPane scrollTable;
    private final Waveform waveForm = new Waveform();
    private final Spectrogram spec = new Spectrogram();
    private final PlaceholderTextField phtf = new PlaceholderTextField();
    private final ColorViewer cv = new ColorViewer();

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        
        scrollTable = new JScrollPane(table);
        scrollTable.setLocation(100, 10);
        scrollTable.setSize(300, 200);
        getContentPane().add(scrollTable);
        
        table.addLine("Text color", java.awt.Color.yellow);
        table.addLine("Karaoke color", java.awt.Color.red);
        table.addLine("Outline color", java.awt.Color.blue);
        table.addLine("Shadow color", java.awt.Color.black);
        KKList<String> fruits = new KKList<>();
        fruits.getList().add("Pineapple");
        fruits.getList().add("Apple");
        fruits.getList().add("Banana");
        table.addLine("Fruit", fruits);
        table.addLine("Happy", new KKCheckBox());
        table.addLine("Happy", "Fairy Tail");
        table.addLine("Integer", 0);
        table.addLine("Double", 0d);
        table.addLine("Color", new KKWithDialog(java.awt.Color.yellow));
        
        waveForm.setLocation(100, 250);
        waveForm.setSize(600, 100);
        waveForm.setPath("C:\\Users\\util2\\Desktop\\Tests\\konnpetkoll\\00.wav");
        waveForm.setTime(12.25d, 15.348d);
        getContentPane().add(waveForm);
        waveForm.repaint();
        waveForm.setSecAreaStart(13.5d);
        waveForm.setSecAreaEnd(14.5d);
        
        spec.setLocation(100, 400);
        spec.setSize(600, 100);
        spec.setPath("C:\\Users\\util2\\Desktop\\Tests\\konnpetkoll\\00.wav");
        spec.setTime(14d, 15d);
        getContentPane().add(spec);
        spec.repaint();
        
        phtf.setLocation(100, 230);
        phtf.setSize(300, 20);
        phtf.setPlaceholder("Type your surname");
        getContentPane().add(phtf);
        
        cv.setLocation(100, 510);
        cv.setSize(300, 50);
        getContentPane().add(cv);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 559, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
