package org.wingate.konnpetkoll;

import java.awt.EventQueue;

/**
 * Konnpet Koll
 * @author util2
 */
public class Konnpetkoll {

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            MainFrame mf = new MainFrame();
            mf.setSize(800, 600);
            mf.setLocationRelativeTo(null);
            mf.setVisible(true);
        });
    }
}
