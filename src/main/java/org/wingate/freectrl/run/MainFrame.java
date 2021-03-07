/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.freectrl.run;

import org.wingate.freectrl.demo.SliderCheckBoxDemo;
import org.wingate.freectrl.demo.TreeTableTimelineDemo;

/**
 *
 * @author util2
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        init();
    }
    
    private void init(){
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSlideCheckBox = new javax.swing.JButton();
        btnT3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        btnSlideCheckBox.setText("Launch a SlideCheckBox");
        btnSlideCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSlideCheckBoxActionPerformed(evt);
            }
        });
        jPanel1.add(btnSlideCheckBox);

        btnT3.setText("Launch a TreeTableTimeline");
        btnT3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT3ActionPerformed(evt);
            }
        });
        jPanel1.add(btnT3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSlideCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSlideCheckBoxActionPerformed
        SliderCheckBoxDemo demo = new SliderCheckBoxDemo();
        demo.setVisible(true);
    }//GEN-LAST:event_btnSlideCheckBoxActionPerformed

    private void btnT3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT3ActionPerformed
        TreeTableTimelineDemo demo = new TreeTableTimelineDemo();
        demo.setVisible(true);
    }//GEN-LAST:event_btnT3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSlideCheckBox;
    private javax.swing.JButton btnT3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
