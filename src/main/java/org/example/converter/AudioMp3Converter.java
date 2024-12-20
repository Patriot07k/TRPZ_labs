package org.example.converter;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import org.example.audiotrack.Audiotrack;

import java.io.File;
import java.io.IOException;

public class AudioMp3Converter implements Converter<File> {
    private static AudioMp3Converter INSTANCE;
    private EncodingAttributes encodingAttributes;
    private Encoder encoder;

    private AudioMp3Converter() {
        encodingAttributes = new EncodingAttributes();
        encodingAttributes.setFormat("mp3");
        encoder = new Encoder();
    }

    public static AudioMp3Converter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AudioMp3Converter();
        }
        return INSTANCE;
    }

    @Override
    public File convertTo(Audiotrack audiotrack) {
        encodingAttributes.setAudioAttributes(audiotrack.getAudioAttributes());

        try {
            File convertedMp3 = new File(audiotrack.getFileLink().getParent() + "\\" + audiotrack.getFileLink().getName() + " (converted to mp3).mp3");
            convertedMp3.createNewFile();
            encoder.encode(audiotrack.getFileLink(), convertedMp3, encodingAttributes);
            return convertedMp3;
        } catch (EncoderException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
