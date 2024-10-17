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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import org.wingate.konnpetkoll.util.DrawColor;

/**
 *
 * @author util2
 */
public class Waveform extends JPanel {
    
    private String path = null;
    
    private BufferedImage image = null;
    private double secStart = 0d, oldSecStart = -1d;
    private double secEnd = 0d, oldSecEnd = -1d;
    
    private Color backgroundColor = Color.black;
    private Color outerColor = Color.blue.brighter();
    private Color innerColor = Color.blue.darker();
    
    private boolean secondsMarkEnable = true;
    private boolean secondsTimeEnable = true;
    private Color secondsMarkColor = Color.white;
    private Color secondsTimeColor = Color.white;
    private float secondsMarkAlpha = .5f;
    private float secondsTimeAlpha = .7f;
    
    private boolean cursorMarkEnable = true;
    private boolean cursorTimeEnable = true;
    private Color cursorMarkColor = Color.pink;
    private Color cursorTimeColor = Color.pink;
    private float cursorMarkAlpha = .8f;
    private float cursorTimeAlpha = .8f;
    private int xCursor = -1;
    
    private boolean areaStartMarkEnable = true;
    private boolean areaStartTimeEnable = true;
    private Color areaStartMarkColor = Color.cyan;
    private Color areaStartTimeColor = Color.cyan;
    private float areaStartMarkAlpha = .8f;
    private float areaStartTimeAlpha = .8f;
    private boolean areaEndMarkEnable = true;
    private boolean areaEndTimeEnable = true;
    private Color areaEndMarkColor = Color.cyan;
    private Color areaEndTimeColor = Color.cyan;
    private float areaEndMarkAlpha = .8f;
    private float areaEndTimeAlpha = .8f;
    private boolean areaSelectionEnable = true;
    private Color areaSelectionColor = Color.cyan;
    private float areaSelectionAlpha = .5f;
    private int xAreaStart = -1;
    private int xAreaEnd = -1;

    /**
     * Creates new form Waveform
     */
    public Waveform() {
        initComponents();
        
        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                xCursor = e.getX();
                repaint();
            }
        });
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch(e.getButton()){
                    case MouseEvent.BUTTON1 -> {
                        xAreaStart = e.getX();
                        repaint();
                    }
                    case MouseEvent.BUTTON3 -> {
                        xAreaEnd = e.getX();
                        repaint();
                    }
                    case MouseEvent.BUTTON2 -> {
                        // Reset
                        xAreaStart = -1;
                        xAreaEnd = -1;
                        repaint();
                    }
                }
            }
        });
    }
    
    public void setPath(String path){
        this.path = path;
    }
    
    public void setTime(double secStart, double secEnd){
        this.secStart = secStart;
        this.secEnd = secEnd;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        if(path != null && secStart != secEnd){
            try {
                
                if(secStart != oldSecStart && secEnd != oldSecEnd){
                    doBufferedImage();
                    oldSecStart = secStart;
                    oldSecEnd = secEnd;
                }
                
                g2d.drawImage(image, 0, 0, null);
                
                // Seconds
                if(secondsMarkEnable == true){
                    double secondsPerPixel = (secEnd - secStart) / image.getWidth();
                    double t = secStart;
                    double lastInteger = -1d;
                    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 10f));
                    for(int x=0; x<image.getWidth(); x++){
                        double value = getTrueInteger(t, secondsPerPixel);
                        if(Math.round(value) != Math.round(lastInteger) && value != -1d){
                            //---
                            g2d.setColor(DrawColor.getClosest(secondsMarkColor, secondsMarkAlpha));
                            g2d.draw(new Line2D.Float(x, 0, x, image.getHeight()));

                            //---
                            if(secondsTimeEnable == true){
                                String stime = getTimeString(value);
                                int measure = g2d.getFontMetrics().stringWidth(stime);

                                g2d.setColor(DrawColor.getClosest(secondsTimeColor, secondsTimeAlpha));
                                g2d.translate(x, 2 + measure);
                                g2d.rotate(Math.toRadians(-90));

                                g2d.drawString(stime, 0, 0);
                                g2d.rotate(Math.toRadians(90));
                                g2d.translate(-x, -(2 + measure));
                            }                        
                        }
                        t += secondsPerPixel;
                        lastInteger = value;
                    }
                }

                // Area (Start)
                if(xAreaStart != xAreaEnd && areaStartMarkEnable == true){
                    //---
                    g2d.setColor(DrawColor.getClosest(areaStartMarkColor, areaStartMarkAlpha));
                    g2d.draw(new Line2D.Double(xAreaStart, 0, xAreaStart, image.getHeight()));

                    //---
                    if(areaStartTimeEnable == true){
                        double secs = secStart + (xAreaStart * (secEnd - secStart) / image.getWidth());
                        String stime = getTimeString(secs);
                        int measure = g2d.getFontMetrics().stringWidth(stime);

                        g2d.setColor(DrawColor.getClosest(areaStartTimeColor, areaStartTimeAlpha));
                        g2d.translate(xAreaStart, 2 + measure);
                        g2d.rotate(Math.toRadians(-90));

                        g2d.drawString(stime, 0, 0);
                        g2d.rotate(Math.toRadians(90));
                        g2d.translate(-xAreaStart, -(2 + measure));
                    }
                }

                // Area (End)
                if(xAreaStart != xAreaEnd && areaEndMarkEnable == true){
                    //---
                    g2d.setColor(DrawColor.getClosest(areaEndMarkColor, areaEndMarkAlpha));
                    g2d.draw(new Line2D.Double(xAreaEnd, 0, xAreaEnd, image.getHeight()));

                    //---
                    if(areaEndTimeEnable == true){
                        double secs = secStart + (xAreaEnd * (secEnd - secStart) / image.getWidth());
                        String stime = getTimeString(secs);
                        int measure = g2d.getFontMetrics().stringWidth(stime);

                        g2d.setColor(DrawColor.getClosest(areaEndTimeColor, areaEndTimeAlpha));
                        g2d.translate(xAreaEnd, 2 + measure);
                        g2d.rotate(Math.toRadians(-90));

                        g2d.drawString(stime, 0, 0);
                        g2d.rotate(Math.toRadians(90));
                        g2d.translate(-xAreaEnd, -(2 + measure));
                    }
                }

                // Area (show selection)
                if(xAreaStart != xAreaEnd && areaSelectionEnable == true){
                    //---
                    g2d.setColor(DrawColor.getClosest(areaSelectionColor, areaSelectionAlpha));
                    g2d.fill(new Rectangle2D.Double(xAreaStart, 0, xAreaEnd - xAreaStart, image.getHeight()));
                }

                // Cursor
                if(cursorMarkEnable == true){
                    //---
                    g2d.setColor(DrawColor.getClosest(cursorMarkColor, cursorMarkAlpha));
                    g2d.draw(new Line2D.Float(xCursor, 0, xCursor, image.getHeight()));

                    //---
                    if(cursorTimeEnable == true){
                        double secs = secStart + (xCursor * (secEnd - secStart) / image.getWidth());
                        String stime = getTimeString(secs);
                        int measure = g2d.getFontMetrics().stringWidth(stime);

                        g2d.setColor(DrawColor.getClosest(cursorTimeColor, cursorTimeAlpha));
                        g2d.translate(xCursor, 2 + measure);
                        g2d.rotate(Math.toRadians(-90));

                        g2d.drawString(stime, 0, 0);
                        g2d.rotate(Math.toRadians(90));
                        g2d.translate(-xCursor, -(2 + measure));
                    }
                }
                
            } catch (UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(Waveform.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public void doBufferedImage() throws UnsupportedAudioFileException, IOException{
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path))) {
            AudioFormat format = ais.getFormat();
            
            int bytesPerFrame = ais.getFormat().getFrameSize();
            //long frames = ais.getFrameLength();
            //double durSeconds = (double)frames / format.getFrameRate();
            
            long startFrame = Math.round(secStart * format.getFrameRate());
            long endFrame = Math.round(secEnd * format.getFrameRate());
            
            long startBytes = startFrame * bytesPerFrame;
            long endBytes = endFrame * bytesPerFrame;
            
            if(startBytes != 0L) ais.skip(startBytes);
            byte[] audioBuffer = new byte[(int)(endBytes - startBytes)];
            
            ais.read(audioBuffer, 0, audioBuffer.length);
            
            //Draw
            // Background
            g.setColor(backgroundColor);
            g.fillRect(0, 0, image.getWidth(), image.getHeight());
            
            int bytesPerPixel = (int)((endBytes - startBytes) / image.getWidth());
            
            // Copie à l'identique du tableau afin de parcourir en entier
            // les bytes audio (audioBuffer) et traitement par pixel
            byte[] audio = Arrays.copyOf(audioBuffer, audioBuffer.length);
            int progression = 0;
            
            int middle = getHeight() / 2;
            
            for(int x=0; x<image.getWidth(); x++){
                // Copie d'une partie de l'audio suivant la progression
                byte[] pixel = Arrays.copyOfRange(
                        audio, progression, progression + bytesPerPixel);
                progression += bytesPerPixel;
                
                g.setColor(outerColor);
                float doAverageVar = doAverage(pixel);
                g.draw(new Line2D.Float(x, middle, x, doAverageVar));
                g.draw(new Line2D.Float(x, middle, x, 100 - doAverageVar));
                
                g.setColor(innerColor);
                float doRootMeanSquareVar = doRootMeanSquare(pixel);
                g.draw(new Line2D.Float(x, middle, x, doRootMeanSquareVar));
                g.draw(new Line2D.Float(x, middle, x, 100 - doRootMeanSquareVar));
            }
            
        }
        
        g.dispose();
    }
    
    // from -1 to 1 on 1 pixel
    private float doAverage(byte[] samples){
        float sum = 0;
        for(int i=0; i<samples.length; i++){
            byte b = samples[i];
            sum += b < 0 ? -b : b;
        }
        return (sum * 2) / samples.length;
    }
    
    // from -1 to 1 on 1 pixel
    private float doRootMeanSquare(byte[] samples){
        float squaredsum = 0;
        for(int i=0; i<samples.length; i++){
            byte b = samples[i];
            squaredsum += (b * b);
        }
        float mean = squaredsum / samples.length;
        return (float)Math.sqrt(mean);
    }
    
    private double getTrueInteger(double value, double step){
        if(Double.isNaN(value) || Double.isInfinite(value)) return -1d;
        double v_min = value - step;
        double v_max = value + step;
        for(double v = v_min; v <= v_max; v += 0.001d){
            if(String.format("%.3f", v).endsWith("000")){
                return v;
            }
        }
        return -1d;
    }
    
    private String getTimeString(double secs){
        long ms = Math.round(secs * 1000d);
        int hour = (int)(ms / 3600000);
        int min = (int)((ms - 3600000 * hour) / 60000);
        int sec = (int)((ms - 3600000 * hour - 60000 * min) / 1000);
        int cs = (int)(ms - 3600000 * hour - 60000 * min - 1000 * sec) / 10;
        
        return String.format(
                "%s:%s:%s.%s",
                hour,
                min < 10 ? "0" + min : min,
                sec < 10 ? "0" + sec : sec,
                cs < 10 ? "0" + cs : cs
        );
    }
    
    public BufferedImage getImage(){
        return image;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint();
    }

    public Color getOuterColor() {
        return outerColor;
    }

    public void setOuterColor(Color outerColor) {
        this.outerColor = outerColor;
        repaint();
    }

    public Color getInnerColor() {
        return innerColor;
    }

    public void setInnerColor(Color innerColor) {
        this.innerColor = innerColor;
        repaint();
    }

    public boolean isSecondsMarkEnable() {
        return secondsMarkEnable;
    }

    public void setSecondsMarkEnable(boolean secondsMarkEnable) {
        this.secondsMarkEnable = secondsMarkEnable;
        repaint();
    }

    public boolean isSecondsTimeEnable() {
        return secondsTimeEnable;
    }

    public void setSecondsTimeEnable(boolean secondsTimeEnable) {
        this.secondsTimeEnable = secondsTimeEnable;
        repaint();
    }

    public Color getSecondsMarkColor() {
        return secondsMarkColor;
    }

    public void setSecondsMarkColor(Color secondsMarkColor) {
        this.secondsMarkColor = secondsMarkColor;
        repaint();
    }

    public Color getSecondsTimeColor() {
        return secondsTimeColor;
    }

    public void setSecondsTimeColor(Color secondsTimeColor) {
        this.secondsTimeColor = secondsTimeColor;
        repaint();
    }

    public float getSecondsMarkAlpha() {
        return secondsMarkAlpha;
    }

    public void setSecondsMarkAlpha(float secondsMarkAlpha) {
        this.secondsMarkAlpha = secondsMarkAlpha;
        repaint();
    }

    public float getSecondsTimeAlpha() {
        return secondsTimeAlpha;
    }

    public void setSecondsTimeAlpha(float secondsTimeAlpha) {
        this.secondsTimeAlpha = secondsTimeAlpha;
        repaint();
    }

    public boolean isCursorMarkEnable() {
        return cursorMarkEnable;
    }

    public void setCursorMarkEnable(boolean cursorMarkEnable) {
        this.cursorMarkEnable = cursorMarkEnable;
        repaint();
    }

    public boolean isCursorTimeEnable() {
        return cursorTimeEnable;
    }

    public void setCursorTimeEnable(boolean cursorTimeEnable) {
        this.cursorTimeEnable = cursorTimeEnable;
        repaint();
    }

    public Color getCursorMarkColor() {
        return cursorMarkColor;
    }

    public void setCursorMarkColor(Color cursorMarkColor) {
        this.cursorMarkColor = cursorMarkColor;
        repaint();
    }

    public Color getCursorTimeColor() {
        return cursorTimeColor;
    }

    public void setCursorTimeColor(Color cursorTimeColor) {
        this.cursorTimeColor = cursorTimeColor;
        repaint();
    }

    public float getCursorMarkAlpha() {
        return cursorMarkAlpha;
    }

    public void setCursorMarkAlpha(float cursorMarkAlpha) {
        this.cursorMarkAlpha = cursorMarkAlpha;
        repaint();
    }

    public float getCursorTimeAlpha() {
        return cursorTimeAlpha;
    }

    public void setCursorTimeAlpha(float cursorTimeAlpha) {
        this.cursorTimeAlpha = cursorTimeAlpha;
        repaint();
    }

    public double getSecAreaStart() {
        return secStart + (xAreaStart * (secEnd - secStart) / image.getWidth());
    }

    public void setSecAreaStart(double secAreaStart) {
        double rec = secAreaStart - secStart;
        xAreaStart = (int)Math.round(rec * getWidth() / (secEnd - secStart));
        repaint();
    }

    public double getSecAreaEnd() {
        return secStart + (xAreaEnd * (secEnd - secStart) / image.getWidth());
    }

    public void setSecAreaEnd(double secAreaEnd) {
        double rec = secAreaEnd - secStart;
        xAreaEnd = (int)Math.round(rec * getWidth() / (secEnd - secStart));
        repaint();
    }

    public boolean isAreaStartMarkEnable() {
        return areaStartMarkEnable;
    }

    public void setAreaStartMarkEnable(boolean areaStartMarkEnable) {
        this.areaStartMarkEnable = areaStartMarkEnable;
        repaint();
    }

    public boolean isAreaStartTimeEnable() {
        return areaStartTimeEnable;
    }

    public void setAreaStartTimeEnable(boolean areaStartTimeEnable) {
        this.areaStartTimeEnable = areaStartTimeEnable;
        repaint();
    }

    public Color getAreaStartMarkColor() {
        return areaStartMarkColor;
    }

    public void setAreaStartMarkColor(Color areaStartMarkColor) {
        this.areaStartMarkColor = areaStartMarkColor;
        repaint();
    }

    public Color getAreaStartTimeColor() {
        return areaStartTimeColor;
    }

    public void setAreaStartTimeColor(Color areaStartTimeColor) {
        this.areaStartTimeColor = areaStartTimeColor;
        repaint();
    }

    public float getAreaStartMarkAlpha() {
        return areaStartMarkAlpha;
    }

    public void setAreaStartMarkAlpha(float areaStartMarkAlpha) {
        this.areaStartMarkAlpha = areaStartMarkAlpha;
        repaint();
    }

    public float getAreaStartTimeAlpha() {
        return areaStartTimeAlpha;
    }

    public void setAreaStartTimeAlpha(float areaStartTimeAlpha) {
        this.areaStartTimeAlpha = areaStartTimeAlpha;
        repaint();
    }

    public boolean isAreaEndMarkEnable() {
        return areaEndMarkEnable;
    }

    public void setAreaEndMarkEnable(boolean areaEndMarkEnable) {
        this.areaEndMarkEnable = areaEndMarkEnable;
        repaint();
    }

    public boolean isAreaEndTimeEnable() {
        return areaEndTimeEnable;
    }

    public void setAreaEndTimeEnable(boolean areaEndTimeEnable) {
        this.areaEndTimeEnable = areaEndTimeEnable;
        repaint();
    }

    public Color getAreaEndMarkColor() {
        return areaEndMarkColor;
    }

    public void setAreaEndMarkColor(Color areaEndMarkColor) {
        this.areaEndMarkColor = areaEndMarkColor;
        repaint();
    }

    public Color getAreaEndTimeColor() {
        return areaEndTimeColor;
    }

    public void setAreaEndTimeColor(Color areaEndTimeColor) {
        this.areaEndTimeColor = areaEndTimeColor;
        repaint();
    }

    public float getAreaEndMarkAlpha() {
        return areaEndMarkAlpha;
    }

    public void setAreaEndMarkAlpha(float areaEndMarkAlpha) {
        this.areaEndMarkAlpha = areaEndMarkAlpha;
        repaint();
    }

    public float getAreaEndTimeAlpha() {
        return areaEndTimeAlpha;
    }

    public void setAreaEndTimeAlpha(float areaEndTimeAlpha) {
        this.areaEndTimeAlpha = areaEndTimeAlpha;
        repaint();
    }

    public boolean isAreaSelectionEnable() {
        return areaSelectionEnable;
    }

    public void setAreaSelectionEnable(boolean areaSelectionEnable) {
        this.areaSelectionEnable = areaSelectionEnable;
        repaint();
    }

    public Color getAreaSelectionColor() {
        return areaSelectionColor;
    }

    public void setAreaSelectionColor(Color areaSelectionColor) {
        this.areaSelectionColor = areaSelectionColor;
        repaint();
    }

    public float getAreaSelectionAlpha() {
        return areaSelectionAlpha;
    }

    public void setAreaSelectionAlpha(float areaSelectionAlpha) {
        this.areaSelectionAlpha = areaSelectionAlpha;
        repaint();
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