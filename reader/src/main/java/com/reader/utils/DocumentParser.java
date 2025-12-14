package com.reader.utils;

import com.reader.parsers.*;

import java.io.File;

/**
 * Factory pattern to detect file type and route to appropriate parser.
 * Returns extracted text as String.
 */
public class DocumentParser {
    
    public static String parse(File file) throws Exception {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }

        String fileName = file.getName().toLowerCase();
        String extension = getFileExtension(fileName);

        return switch (extension) {
            case "txt" -> TextExtractor.extract(file);
            case "pdf" -> PDFParser.extract(file);
            case "epub" -> EPUBParser.extract(file);
            case "doc", "docx" -> DOCParser.extract(file);
            default -> throw new UnsupportedOperationException(
                "Unsupported file format: " + extension);
        };
    }

    private static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot == -1 || lastDot == fileName.length() - 1) {
            throw new IllegalArgumentException("File has no extension: " + fileName);
        }
        return fileName.substring(lastDot + 1);
    }
}

