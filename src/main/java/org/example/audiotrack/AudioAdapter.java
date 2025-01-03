package org.example.audiotrack;

import it.sauronsoftware.jave.AudioAttributes;

import java.io.File;

public class AudioAdapter{
    private final Audiotrack audiotrack;

    public AudioAdapter(Audiotrack audiotrack) {
        this.audiotrack = audiotrack;
    }

    public AudioAttributes adaptAttributes() {
        return audiotrack.getAudioAttributes();
    }

    public File adaptFile() {
        return audiotrack.getFileLink();
    }
}

