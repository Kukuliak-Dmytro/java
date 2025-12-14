package com.reader.parsers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/**
 * Uses PDFBox to extract text from PDF files.
 * Handles multi-page documents and returns concatenated text.
 */
public class PDFParser {
    
    public static String extract(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(1);
            stripper.setEndPage(document.getNumberOfPages());
            
            String text = stripper.getText(document);
            return text != null ? text : "";
        }
    }
}

