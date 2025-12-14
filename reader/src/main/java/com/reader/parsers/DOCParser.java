package com.reader.parsers;

import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.extractor.POITextExtractor;

import java.io.File;
import java.io.IOException;

/**
 * Uses Apache POI to parse DOC/DOCX files.
 * Extracts text content and handles formatting preservation.
 */
public class DOCParser {
    
    public static String extract(File file) throws IOException {
        try (POITextExtractor extractor = ExtractorFactory.createExtractor(file)) {
            String text = extractor.getText();
            return text != null ? text : "";
        } catch (Exception e) {
            throw new IOException("Failed to extract text from document: " + e.getMessage(), e);
        }
    }
}

