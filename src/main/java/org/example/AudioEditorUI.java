package org.example;

import org.example.converter.AudioFlacConverter;
import org.example.converter.AudioMp3Converter;
import org.example.converter.AudioOggConverter;
import org.example.audiotrack.*;
import org.example.swing.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AudioEditorUI {
    private JFrame frame;
    private File selectedFile;
    private JPanel waveformPanel;
    private Logger logger;

    public AudioEditorUI() {

        logger = new Logger();

        frame = new JFrame("Audio Editor");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton loadFileButton = new JButton("Load Audio File");
        loadFileButton.setBounds(50, 50, 180, 30);
        frame.add(loadFileButton);

        JLabel fileLabel = new JLabel("No file selected");
        fileLabel.setBounds(250, 50, 300, 30);
        frame.add(fileLabel);

        JButton convertToMp3Button = new JButton("Convert to MP3");
        convertToMp3Button.setBounds(50, 100, 180, 30);
        frame.add(convertToMp3Button);

        JButton convertToOggButton = new JButton("Convert to OGG");
        convertToOggButton.setBounds(50, 150, 180, 30);
        frame.add(convertToOggButton);

        JButton convertToFlacButton = new JButton("Convert to FLAC");
        convertToFlacButton.setBounds(50, 200, 180, 30);
        frame.add(convertToFlacButton);

        waveformPanel = new JPanel();
        waveformPanel.setBounds(50, 250, 500, 300);
        waveformPanel.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
        frame.add(waveformPanel);


        loadFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                fileLabel.setText("Selected: " + selectedFile.getName());
                displayWaveform(selectedFile);
                logger.fileOpen();
            }
        });

        convertToMp3Button.addActionListener(e -> convertAudio("mp3"));
        convertToOggButton.addActionListener(e -> convertAudio("ogg"));
        convertToFlacButton.addActionListener(e -> convertAudio("flac"));

        frame.setVisible(true);
    }

    private void convertAudio(String format) {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(frame, "Please load a file first.");
            return;
        }

        AudioAdapter adapter = createAudioAdapter(selectedFile);
        File convertedFile;
        switch (format) {
            case "mp3":
                convertedFile = AudioMp3Converter.getInstance().convertTo(new Mp3(adapter.adaptFile().getAbsolutePath()));
                break;
            case "ogg":
                convertedFile = AudioOggConverter.getInstance().convertTo(new Ogg(adapter.adaptFile().getAbsolutePath()));
                break;
            case "flac":
                convertedFile = AudioFlacConverter.getInstance().convertTo(new Flac(adapter.adaptFile().getAbsolutePath()));
                break;
            default:
                throw new IllegalArgumentException("Unsupported format: " + format);
        }
    }

    private Audiotrack createAudiotrack(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".mp3")) {
            return new Mp3(file.getAbsolutePath());
        } else if (fileName.endsWith(".ogg")) {
            return new Ogg(file.getAbsolutePath());
        } else if (fileName.endsWith(".flac")) {
            return new Flac(file.getAbsolutePath());
        } else {
            throw new IllegalArgumentException("Unsupported file format: " + fileName);
        }
    }
    private void displayWaveform(File audioFile) {
        waveformPanel.removeAll();
        waveformPanel.setLayout(new BorderLayout());
        waveformPanel.add(new AudioWaveformPanel(audioFile), BorderLayout.CENTER);
        waveformPanel.revalidate();
        waveformPanel.repaint();
    }
    private AudioAdapter createAudioAdapter(File file) {
        Audiotrack audiotrack = createAudiotrack(file);
        return new AudioAdapter(audiotrack);
    }



    public static void main(String[] args) {
        new AudioEditorUI();
    }
}
