package org.example;

import org.example.audiotrack.Flac;
import org.example.database.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        new AudioEditorUI();
    }
}

