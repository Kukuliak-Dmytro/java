package com.reader.parsers;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Uses epub4j to parse EPUB files.
 * Extracts text from all chapters and returns formatted text.
 */
public class EPUBParser {
    
    public static String extract(File file) throws IOException {
        StringBuilder text = new StringBuilder();
        
        try (InputStream inputStream = new FileInputStream(file)) {
            EpubReader epubReader = new EpubReader();
            Book book = epubReader.readEpub(inputStream);
            
            List<SpineReference> spineReferences = book.getSpine().getSpineReferences();
            
            for (SpineReference spineReference : spineReferences) {
                Resource resource = spineReference.getResource();
                try {
                    byte[] data = resource.getData();
                    String content = new String(data, "UTF-8");
                    
                    // Remove HTML tags and extract text content
                    content = content.replaceAll("<[^>]+>", " ");
                    content = content.replaceAll("&nbsp;", " ");
                    content = content.replaceAll("&amp;", "&");
                    content = content.replaceAll("&lt;", "<");
                    content = content.replaceAll("&gt;", ">");
                    content = content.replaceAll("&quot;", "\"");
                    content = content.replaceAll("&#39;", "'");
                    
                    // Clean up multiple spaces and newlines
                    content = content.replaceAll("\\s+", " ");
                    content = content.trim();
                    
                    if (!content.isEmpty()) {
                        text.append(content).append("\n\n");
                    }
                } catch (Exception e) {
                    // Skip resources that can't be read as text
                    System.err.println("Warning: Could not extract text from resource: " + resource.getHref());
                }
            }
        }
        
        return text.toString().trim();
    }
}

