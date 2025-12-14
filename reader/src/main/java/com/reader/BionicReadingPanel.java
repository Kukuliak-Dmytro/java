package com.reader;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel with JTextPane for formatted display.
 * User input for bold letter count (2-4).
 * Method to process text: split words, bold first N letters.
 * Display formatted text with HTML styling.
 * Scrollable text area.
 */
public class BionicReadingPanel extends JPanel {
    private JTextPane textPane;
    private JSpinner boldCountSpinner;
    private String currentText = "";

    public BionicReadingPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10)); // Added spacing
        controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Bottom margin
        
        controlPanel.add(new JLabel("Bold Strength (First Letters):"));
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(3, 1, 5, 1);
        boldCountSpinner = new JSpinner(spinnerModel);
        // Make it slightly bigger
        JComponent editor = boldCountSpinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setColumns(3);
        
        boldCountSpinner.addChangeListener(e -> applyBionicFormatting());
        controlPanel.add(boldCountSpinner);
        
        JButton refreshButton = new JButton("Refresh View");
        refreshButton.addActionListener(e -> applyBionicFormatting());
        controlPanel.add(refreshButton);
        
        add(controlPanel, BorderLayout.NORTH);

        // Text display area
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
        
        // Configure HTML styling
        HTMLEditorKit kit = new HTMLEditorKit();
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body { font-family: 'Segoe UI', 'SansSerif'; font-size: 16pt; padding: 20px; line-height: 1.6; color: #bbbbbb; }");
        // Dark mode text color hint
        
        textPane.setEditorKit(kit);
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")));
        
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setText(String text) {
        currentText = text != null ? text : "";
        applyBionicFormatting();
    }

    private void applyBionicFormatting() {
        if (currentText.isEmpty()) {
            textPane.setText("<html><body>No document loaded. Use File > Open to load a document.</body></html>");
            return;
        }

        int boldCount = (Integer) boldCountSpinner.getValue();
        String formattedText = processBionicText(currentText, boldCount);
        textPane.setText(formattedText);
        textPane.setCaretPosition(0); // Scroll to top
    }

    private String processBionicText(String text, int boldCount) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");

        // Split by whitespace but preserve line breaks
        String[] lines = text.split("\n", -1);
        
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                html.append("<br/>");
                continue;
            }

            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.isEmpty()) continue;

                // Extract bold portion and regular portion
                int boldLength = Math.min(boldCount, word.length());
                String boldPart = word.substring(0, boldLength);
                String regularPart = word.substring(boldLength);

                // Escape HTML special characters
                boldPart = escapeHtml(boldPart);
                regularPart = escapeHtml(regularPart);

                html.append("<b>").append(boldPart).append("</b>").append(regularPart);
                
                if (i < words.length - 1) {
                    html.append(" ");
                }
            }
            html.append("<br/>");
        }

        html.append("</body></html>");
        return html.toString();
    }

    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }
}

