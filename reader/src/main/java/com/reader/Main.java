package com.reader;

import com.reader.utils.DocumentReader;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point for the Document Reader application.
 * Initializes the Swing UI on the Event Dispatch Thread.
 */
public class Main {
    public static void main(String[] args) {
        // Setup modern UI look and feel
        try {
            System.setProperty("flatlaf.useNativeLibrary", "false");
            FlatDarkLaf.setup();
            UIManager.put("Button.arc", 999);
            UIManager.put("Component.arc", 10);
            UIManager.put("ProgressBar.arc", 999);
            UIManager.put("TextComponent.arc", 10);
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        // Initialize Swing UI on EDT
        SwingUtilities.invokeLater(() -> {
            try {
                DocumentReader reader = new DocumentReader();
                reader.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to launch application: " + e.getMessage());
            }
        });
    }
}

