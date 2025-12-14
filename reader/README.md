# Document Reader Application

A simple Java Swing application that allows users to upload documents (TXT, EPUB, DOC, PDF) and read them in two modes: bionic reading (bold first 2-4 letters) and speed reading (configurable WPM up to 1000).

## Features

- **Multiple File Format Support**: TXT, PDF, EPUB, DOC, DOCX
- **Bionic Reading Mode**: Bold first 2-4 letters of each word to improve reading speed and comprehension
- **Speed Reading Mode**: Word-by-word display with configurable words per minute (50-1000 WPM)
- **User-Friendly Interface**: Tabbed interface with menu bar for easy navigation

## Requirements

- Java 11 or higher
- Maven 3.6 or higher
- IntelliJ IDEA (recommended) or any Java IDE

## Setup Instructions

1. **Clone or download the project**

2. **Open in IntelliJ IDEA**
   - File > Open > Select the project directory
   - IntelliJ will automatically detect the Maven project

3. **Wait for Maven dependencies to download**
   - IntelliJ will automatically download dependencies from `pom.xml`
   - Verify in Maven tool window (View > Tool Windows > Maven)
   - Dependencies should appear under "Dependencies" node

4. **Configure Project SDK**
   - File > Project Structure > Project
   - Set Project SDK to Java 11 or higher
   - Set Project language level to match SDK

## Dependencies

The following dependencies are automatically managed by Maven:

- **Apache PDFBox 2.0.27** - PDF parsing
- **Apache POI 5.2.3** - DOC/DOCX parsing
- **epublib-core 3.0** - EPUB parsing
- **Java Swing** - Built-in, no dependency needed

## Usage Guide

1. **Launch the Application**
   - Run `Main.java` from IntelliJ IDEA
   - Or build and run: `mvn clean compile exec:java -Dexec.mainClass="com.reader.Main"`

2. **Load a Document**
   - Click File > Open from the menu bar
   - Select a supported file (TXT, PDF, EPUB, DOC, DOCX)
   - The document will be parsed and loaded

3. **Bionic Reading Mode**
   - Select the "Bionic Reading" tab
   - Adjust the "Bold first letters" spinner (2-4 letters)
   - Text will automatically format with bold prefixes
   - Use the scrollbar to navigate through the document

4. **Speed Reading Mode**
   - Select the "Speed Reading" tab
   - Set your desired words per minute (50-1000 WPM)
   - Click "Start" to begin reading
   - Use "Pause" to pause/resume
   - Use "Stop" to return to the beginning

## Project Structure

```
reader/
├── pom.xml (Maven configuration)
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── reader/
│                   ├── Main.java (entry point)
│                   ├── BionicReadingPanel.java (bionic reading mode)
│                   ├── SpeedReadingPanel.java (speed reading mode)
│                   ├── utils/
│                   │   ├── DocumentReader.java (main UI window)
│                   │   └── DocumentParser.java (handles file parsing)
│                   └── parsers/
│                       ├── PDFParser.java
│                       ├── EPUBParser.java
│                       ├── DOCParser.java
│                       └── TextExtractor.java
└── README.md
```

## Technical Details

### Bionic Reading Implementation
- Splits text into words
- Wraps first N letters (2-4) in `<b>` HTML tags
- Uses JTextPane with HTML content type
- Preserves line breaks and paragraphs

### Speed Reading Implementation
- Splits text into words array
- Calculates delay: 60000 / wpm milliseconds per word
- Uses javax.swing.Timer for word progression
- Displays one word at a time, centered
- Large font (36pt) for readability

### File Format Support
- **TXT**: Direct file reading with UTF-8 encoding
- **PDF**: PDFBox text extraction from all pages
- **EPUB**: epub4j chapter extraction and HTML tag removal
- **DOC/DOCX**: Apache POI text extraction

## Troubleshooting

- **Dependencies not downloading**: Right-click `pom.xml` > Maven > Reload Project
- **File format not supported**: Ensure file extension matches supported formats
- **Parsing errors**: Check that the file is not corrupted and is a valid document
- **Out of memory**: Large PDF files may require increasing JVM heap size

## License

This project is provided as-is for educational purposes.

