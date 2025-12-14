package com.reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JPanel with large text display (JLabel).
 * WPM input field (up to 1000).
 * Start/Pause/Stop controls.
 * Timer-based word display.
 * Word-by-word progression.
 * Centered, large font display.
 */
public class SpeedReadingPanel extends JPanel {
    private JLabel wordLabel;
    private JSpinner wpmSpinner;
    private JButton startButton;
    private JButton pauseButton;
    private JButton stopButton;
    
    private String[] words;
    private int currentWordIndex = 0;
    private Timer timer;
    private boolean isPaused = false;

    public SpeedReadingPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Center: Word Display
        // We put it in a specific panel to center it perfectly
        JPanel displayPanel = new JPanel(new GridBagLayout());
        wordLabel = new JLabel("Reader Ready", JLabel.CENTER);
        wordLabel.setFont(new Font("Segoe UI", Font.BOLD, 64)); // Larger, modern font
        wordLabel.putClientProperty("FlatLaf.styleClass", "h1"); // Hint for headers if supported
        displayPanel.add(wordLabel);
        add(displayPanel, BorderLayout.CENTER);

        // Bottom: Controls
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 15)); // Vertical gap
        
        // 1. Slider/Spinner for WPM (More interactive)
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        settingsPanel.add(new JLabel("Reading Speed (WPM):"));
        
        SpinnerNumberModel wpmModel = new SpinnerNumberModel(300, 50, 2000, 25);
        wpmSpinner = new JSpinner(wpmModel);
        // Important: Update speed in real-time
        wpmSpinner.addChangeListener(e -> updateTimerSpeed());
        
        // Make spinner wider
        JComponent editor = wpmSpinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setColumns(4);
        
        settingsPanel.add(wpmSpinner);
        bottomPanel.add(settingsPanel, BorderLayout.NORTH);

        // 2. Playback Buttons (Big and centered)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        startButton = createStyledButton("Start", new Color(40, 167, 69)); // Greenish hint if possible, else standard
        startButton.addActionListener(e -> startReading());
        
        pauseButton = createStyledButton("Pause", null);
        pauseButton.addActionListener(e -> pauseReading());
        pauseButton.setEnabled(false);
        
        stopButton = createStyledButton("Stop", new Color(220, 53, 69)); // Reddish hint
        stopButton.addActionListener(e -> stopReading());
        stopButton.setEnabled(false);
        
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(stopButton);
        
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color highlight) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        // We can optionally set foregrounds here if we want colors
        if (highlight != null) {
            btn.setForeground(highlight);
            btn.setFont(btn.getFont().deriveFont(Font.BOLD));
        }
        return btn;
    }

    private void updateTimerSpeed() {
        if (timer != null) {
            int wpm = (Integer) wpmSpinner.getValue();
            if (wpm < 1) wpm = 1;
            int delayMs = 60000 / wpm;
            timer.setDelay(delayMs);
        }
    }

    public void setText(String text) {
        stopReading(); // Stop any ongoing reading
        
        if (text == null || text.trim().isEmpty()) {
            words = new String[0];
            wordLabel.setText("No document loaded. Use File > Open to load a document.");
            return;
        }

        // Split text into words
        words = text.split("\\s+");
        currentWordIndex = 0;
        
        if (words.length > 0) {
            wordLabel.setText("Ready. Click Start to begin reading.");
        } else {
            wordLabel.setText("No text found in document.");
        }
    }

    private void startReading() {
        if (words == null || words.length == 0) {
            JOptionPane.showMessageDialog(this,
                "No document loaded. Please load a document first.",
                "No Document",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (isPaused) {
            // Resume from pause
            isPaused = false;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
            return;
        }

        // Start from beginning
        currentWordIndex = 0;
        isPaused = false;
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);

        int wpm = (Integer) wpmSpinner.getValue();
        int delayMs = 60000 / wpm; // milliseconds per word

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(delayMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused && currentWordIndex < words.length) {
                    wordLabel.setText(words[currentWordIndex]);
                    currentWordIndex++;
                } else if (currentWordIndex >= words.length) {
                    // Finished reading
                    stopReading();
                    wordLabel.setText("Reading complete!");
                }
            }
        });

        timer.start();
        // Display first word immediately
        if (words.length > 0) {
            wordLabel.setText(words[0]);
            currentWordIndex = 1;
        }
    }

    private void pauseReading() {
        isPaused = !isPaused;
        if (isPaused) {
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
    }

    private void stopReading() {
        if (timer != null) {
            timer.stop();
        }
        isPaused = false;
        currentWordIndex = 0;
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        pauseButton.setText("Pause");
        stopButton.setEnabled(false);
        
        if (words != null && words.length > 0) {
            wordLabel.setText("Ready. Click Start to begin reading.");
        } else {
            wordLabel.setText("No document loaded.");
        }
    }
}

