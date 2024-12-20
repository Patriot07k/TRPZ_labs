package org.example.audiotrack;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AudioWaveformPanel extends JPanel {
    private byte[] audioBytes;
    private AudioFormat format;

    public AudioWaveformPanel(File audioFile) {
        loadAudioFile(audioFile);
    }

    private void loadAudioFile(File audioFile) {
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile)) {
            format = audioInputStream.getFormat();
            audioBytes = audioInputStream.readAllBytes();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (audioBytes == null) {
            g.drawString("No audio loaded", getWidth() / 2 - 50, getHeight() / 2);
            return;
        }

        // Rendering waveform
        int middleY = getHeight() / 2;
        int length = audioBytes.length / 2;
        int step = Math.max(length / getWidth(), 1);
        g.setColor(Color.BLUE);

        for (int i = 0; i < getWidth(); i++) {
            int sampleIndex = i * step;
            int sampleValue = getSampleValue(sampleIndex);

            int y = middleY - (int) (sampleValue * (middleY - 10) / 32768.0); // Normalize sample value
            g.drawLine(i, middleY, i, y);
        }
    }

    private int getSampleValue(int index) {
        int lowByte = audioBytes[2 * index] & 0xFF;
        int highByte = audioBytes[2 * index + 1];
        return (highByte << 8) | lowByte; // Convert to 16-bit signed value
    }
}