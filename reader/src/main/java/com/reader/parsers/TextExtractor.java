package com.reader.parsers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Simple text file reader for TXT files.
 * Supports UTF-8 encoding.
 */
public class TextExtractor {
    
    public static String extract(File file) throws IOException {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException("Failed to read text file: " + e.getMessage(), e);
        }
    }
}

