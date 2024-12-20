package org.example.converter;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import org.example.audiotrack.Audiotrack;

import java.io.File;
import java.io.IOException;

public class AudioFlacConverter implements Converter<File> {
    private static AudioFlacConverter INSTANCE;
    private EncodingAttributes encodingAttributes;
    private Encoder encoder;

    private AudioFlacConverter() {
        encodingAttributes = new EncodingAttributes();
        encodingAttributes.setFormat("flac");
        encoder = new Encoder();
    }

    public static AudioFlacConverter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AudioFlacConverter();
        }
        return INSTANCE;
    }

    @Override
    public File convertTo(Audiotrack audiotrack) {
        encodingAttributes.setAudioAttributes(audiotrack.getAudioAttributes());
        try {
            File convertedFlac = new File(audiotrack.getFileLink().getParent() + "\\" + audiotrack.getFileLink().getName() + " (converted to flac).flac");
            convertedFlac.createNewFile();
            encoder.encode(audiotrack.getFileLink(), convertedFlac, encodingAttributes);
            return convertedFlac;
        } catch (EncoderException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
