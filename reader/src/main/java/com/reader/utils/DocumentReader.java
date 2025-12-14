package com.reader.utils;

import com.reader.BionicReadingPanel;
import com.reader.SpeedReadingPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Main Swing JFrame window for the Document Reader application.
 * Provides menu bar with File > Open, tabbed pane for reading modes,
 * and file upload functionality.
 */
public class DocumentReader extends JFrame {
    private BionicReadingPanel bionicPanel;
    private SpeedReadingPanel speedPanel;
    private String currentText = "";

    public DocumentReader() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Document Reader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> openFile());
        fileMenu.add(openItem);
        
        JMenuItem closeItem = new JMenuItem("Close");
        closeItem.addActionListener(e -> closeFile());
        fileMenu.add(closeItem);
        
        fileMenu.addSeparator();
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Create toolbar with buttons
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton importButton = new JButton("Import File");
        importButton.addActionListener(e -> openFile());
        toolBar.add(importButton);
        
        toolBar.addSeparator();
        
        JButton closeButton = new JButton("Close File");
        closeButton.addActionListener(e -> closeFile());
        toolBar.add(closeButton);
        
        add(toolBar, BorderLayout.NORTH);

        // Create tabbed pane for reading modes
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add outer padding
        
        bionicPanel = new BionicReadingPanel();
        speedPanel = new SpeedReadingPanel();
        
        tabbedPane.addTab("Bionic Reading", bionicPanel);
        tabbedPane.addTab("Speed Reading", speedPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void openFile() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Supported Files (TXT, PDF, EPUB, DOC, DOCX)", 
                "txt", "pdf", "epub", "doc", "docx"
            ));
            
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                loadDocument(selectedFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error opening file selector: " + e.getMessage(),
                "Import Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDocument(File file) {
        try {
            String text = DocumentParser.parse(file);
            currentText = text;
            
            // Update both reading panels
            bionicPanel.setText(text);
            speedPanel.setText(text);
            
            setTitle("Document Reader - " + file.getName());
            
            JOptionPane.showMessageDialog(this, 
                "Document loaded successfully!\n" + 
                "File: " + file.getName() + "\n" +
                "Characters: " + text.length(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading document: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void closeFile() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to close the current document?",
            "Close Document",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            currentText = "";
            bionicPanel.setText("");
            speedPanel.setText("");
            setTitle("Document Reader");
        }
    }
}

