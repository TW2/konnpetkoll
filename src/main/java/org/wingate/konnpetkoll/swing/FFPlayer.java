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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JPanel;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 *
 * @author util2
 */
public class FFPlayer extends JPanel {
    
    public enum Control {
        Initial, Play, Pause, Stop, Free;
    }
    
    private String path;
    private Control control;
    private BufferedImage videoImage;
    private BufferedImage overlayImage;
    private long totalTime;
    private long currentTime;
    private volatile Thread playTh;
    private boolean onMouseOver = false;

    /**
     * Creates new form FFPlayer
     */
    public FFPlayer() {
        initComponents();
        setDoubleBuffered(true);
        
        path = null;
        control = Control.Initial;
        videoImage = null;
        overlayImage = null;
        totalTime = -1L;
        currentTime = -1L;
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                onMouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                onMouseOver = false;
                repaint();
            }
        });
    }
    
    public void play(){
        switch(control){
            case Initial, Stop, Free -> { control = Control.Play; startPlayback(); }
            case Pause -> { control = Control.Play; }
            case Play -> { control = Control.Pause; }
        }
    }
    
    public void pause(){
        switch(control){
            case Pause -> { control = Control.Play; }
            case Play -> { control = Control.Pause; }
        }
    }
    
    public void stop(){
        switch(control){
            case Pause, Play -> {
                control = Control.Stop;
                stopPlayback();
                free();
            }
        }
    }
    
    public void free(){
        control = Control.Free;
        
        videoImage = null;
        overlayImage = null;
        totalTime = -1L;
        currentTime = -1L;
        repaint();
    }
    
    public void setMediaPath(String path){
        this.path = path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        if(videoImage != null){
            Rectangle r = getRect(
                    getWidth(), getHeight(),
                    videoImage.getWidth(), videoImage.getHeight()
            );
            g2d.drawImage(videoImage, r.x, r.y, r.width, r.height, null);
            
            regenInfo();
        
            if(overlayImage != null){
                g2d.drawImage(overlayImage, r.x, r.y, r.width, r.height, null);
            }
        }
        
        
        
        // TODO subtitles srt and webvtt
    }
    
    /**
     * Returns dimensions of image to apply from ratio boundaries.
     * @param wc component width
     * @param hc component height
     * @param wi image width
     * @param hi image height
     * @return Returns a rectangle which represents limits
     */
    private Rectangle getRect(float wc, float hc, float wi, float hi){
        float x, y, w, h;
        
        float rX = wc / wi;
        float rY = hc / hi;
        float r = rX >= rY ? rY : rX;
        
        w = wi * r;
        h = hi * r;
        x = (wc - w) / 2f;
        y = (hc - h) / 2f;
        
        return new Rectangle(
                Math.round(x),
                Math.round(y),
                Math.round(w),
                Math.round(h)
        );
    }
    
    private void regenInfo(){
        if(totalTime <= 0) return;
        if(onMouseOver == true){
            overlayImage = new BufferedImage(
                    videoImage.getWidth(),
                    videoImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g2d = overlayImage.createGraphics();

            double percent = (double)currentTime / (double)totalTime;

            g2d.setColor(Color.white);
            Rectangle2D rFill = new Rectangle2D.Double(
                    10,
                    videoImage.getHeight() - 22,
                    (videoImage.getWidth() - 20) * percent,
                    10
            );
            g2d.fill(rFill);

            g2d.setColor(Color.white);
            Rectangle2D rDraw = new Rectangle2D.Double(
                    10,
                    videoImage.getHeight() - 22,
                    videoImage.getWidth() - 20,
                    10
            );
            g2d.draw(rDraw);

            g2d.dispose();
        }else{
            overlayImage = null;
        }
    }
    
    @SuppressWarnings("SleepWhileInLoop")
    private void startPlayback(){
        if(path == null) return;
        playTh = new Thread(() -> {
            try {
                final FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(path);
                grabber.start();
                
                totalTime = grabber.getLengthInTime();
                final PlaybackTimer playbackTimer;
                final SourceDataLine soundLine;
                
                if(grabber.getAudioChannels() > 0){
                    // Yeah, got audio
                    final AudioFormat audioFormat = new AudioFormat(
                            grabber.getSampleRate(),
                            16,
                            grabber.getAudioChannels(),
                            true,
                            true
                    );
                    final DataLine.Info info = new DataLine.Info(
                            SourceDataLine.class, audioFormat
                    );
                    
                    soundLine = (SourceDataLine)AudioSystem.getLine(info);
                    soundLine.open(audioFormat);
                    soundLine.start();
                    playbackTimer = new PlaybackTimer(soundLine);
                }else{
                    // Oh no, no audio
                    soundLine = null;
                    playbackTimer = new PlaybackTimer();
                }
                
                // Let's create a converter for conversion from Frame to BufferedImage
                final Java2DFrameConverter converter = new Java2DFrameConverter();
                
                // Let's interleave a signal
                final ExecutorService audioExecutor = Executors.newSingleThreadExecutor();
                final ExecutorService imageExecutor = Executors.newSingleThreadExecutor();
                
                // Let's create a limit for our buffer
                final long maxReadAheadBufferMicros = 1000 * 1000L;
                
                // To equilibrate our video
                long lastTimeStamp = -1L;
                
                // Now launch our main loop
                while(!Thread.interrupted()){
                    if(control == Control.Play){
                        final Frame frame = grabber.grab();
                        
                        if(frame == null){
                            // EOF
                            break;
                        }
                        
                        // To launch the counter of time
                        if(lastTimeStamp < 0L){
                            playbackTimer.start();
                        }
                        
                        // Current time
                        lastTimeStamp = frame.timestamp;
                        
                        // For display only
                        if(lastTimeStamp > currentTime){
                            // If the last time is higher than current then
                            currentTime = lastTimeStamp;
                        }
                        
                        // If we have something in frame
                        if(frame.image != null){
                            final Frame imageFrame = frame.clone();
                            
                            imageExecutor.submit(()-> {
                                final BufferedImage img = converter.convert(imageFrame);
                                final long timeStampDeltaMicros =
                                        imageFrame.timestamp - playbackTimer.elapsedMicros();
                                imageFrame.close();
                                if(timeStampDeltaMicros > 0L){
                                    final long delayMillis = timeStampDeltaMicros / 1000L;
                                    try{
                                        Thread.sleep(delayMillis);
                                    }catch(InterruptedException e){
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                videoImage = img;
                                repaint();
                            });
                        }else if(frame.samples != null && soundLine != null){
                            final ShortBuffer channelSamplesShortBuffer =
                                    (ShortBuffer)frame.samples[0];
                            channelSamplesShortBuffer.rewind();
                            
                            final ByteBuffer outBuffer =
                                    ByteBuffer.allocate(channelSamplesShortBuffer.capacity() * 2);
                            
                            for(int i=0; i<channelSamplesShortBuffer.capacity(); i++){
                                short val = channelSamplesShortBuffer.get(i);
                                outBuffer.putShort(val);
                            }
                            
                            audioExecutor.submit(()->{
                                soundLine.write(outBuffer.array(), 0, outBuffer.capacity());
                                outBuffer.clear();
                            });
                        }
                        
                        final long timeStampDeltaMicros =
                                frame.timestamp - playbackTimer.elapsedMicros();
                        if(timeStampDeltaMicros > maxReadAheadBufferMicros){
                            Thread.sleep((timeStampDeltaMicros - maxReadAheadBufferMicros) / 1000);
                        }
                    }
                }
                
                if(!Thread.interrupted()){
                    long delay = (lastTimeStamp - playbackTimer.elapsedMicros()) / 1000 +
                            Math.round(1 / grabber.getFrameRate() * 1000);
                    Thread.sleep(Math.max(0, delay));
                }
                
                grabber.stop();
                grabber.release();
                
                if(soundLine != null){
                    soundLine.stop();
                }
                
                audioExecutor.shutdownNow();
                audioExecutor.awaitTermination(10, TimeUnit.SECONDS);
                
                imageExecutor.shutdownNow();
                imageExecutor.awaitTermination(10, TimeUnit.SECONDS);                
                
            } catch (FFmpegFrameGrabber.Exception | LineUnavailableException | InterruptedException ex) {
                Logger.getLogger(FFPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        playTh.start();
    }
    
    private void stopPlayback(){
        playTh.interrupt();
    }
    
    private class PlaybackTimer{
        private long startTime = -1L;
        private final DataLine soundLine;

        public PlaybackTimer(DataLine soundLine) {
            this.soundLine = soundLine;
        }

        public PlaybackTimer() {
            this(null);
        }
        
        public void start(){
            if(soundLine == null){
                startTime = System.nanoTime();
            }
        }
        
        public long elapsedMicros(){
            if(soundLine == null){
                if(startTime < 0L){
                    throw new IllegalStateException("PlaybackTimer not initilized!");
                }
                return (System.nanoTime() - startTime) / 1000;
            }else{
                return soundLine.getMicrosecondPosition();
            }
        }
        
    }

    public long getTotalTime() {
        return totalTime;
    }

    public long getCurrentTime() {
        return currentTime;
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
