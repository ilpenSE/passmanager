package dev.elite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * <h1>FileOperations</h1>
 * <p>This class has been made for making the file operations like writing or reading easier.</p>
 * <p>You can use it like: {@code FileOperations f = new FileOperations(myFile);}</p>
 * <p>Methods: {@link #readAllLines()}, {@link #writeToFile(List)}, {@link #editLine(int, String)}, {@link #editLine(String, String)}, {@link #appendLine(String)}, {@link #appendLine(String, int)}, {@link #removeLine(String)}, {@link #removeLine(int)}, {@link #getFileExtension()}, {@link #getLineString()}, {@link #getLineString(String)}</p>
 * @author ilpeN
 * @version 1.0
 */
public class FileOperations {
    private final File FILE;
    public FileOperations(File file) {
        this.FILE = file;
    }

    /**
     * @return List of all lines in that file.
     */
    public ArrayList<String> readAllLines() {
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    /**
     * @return Extension of that file like json, java, js, css, txt etc.
     */
    public String getFileExtension() {
        String[] a = FILE.getName().split("\\.");
        return a[a.length - 1];
    }

    /**
     * @return Get all lines in one string without delimiter.
     */
    public String getLineString() {
        StringBuilder s = new StringBuilder();
        for (String line : readAllLines()) {
            s.append(line);
        }
        return s.toString();
    }

    /**
     * @return Get all lines in one string with delimiter.
     * @param delimiter The string that will go between the lines
     */
    public String getLineString(String delimiter) {
        StringBuilder s = new StringBuilder();
        ArrayList<String> lines = readAllLines();
        for (String line : lines) {
            s.append(Objects.equals(line, lines.get(lines.size() - 1)) ? line : line + delimiter);
        }
        return s.toString();
    }

    /**
     * @param lines List of all lines in that file
     */
    public void writeToFile(List<String> lines) {
        try {
            Files.write(Path.of(FILE.getAbsolutePath()), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param oldLine The old line that will be edited
     * @param newLine The new line that will be replaced with old line.
     */
    public void editLine(String oldLine, String newLine) {
        ArrayList<String> lines = readAllLines();
        List<String> list = lines.stream().map(l -> l.equals(oldLine) ? newLine : l).toList();
        writeToFile(list);
    }

    /**
     * @param lineIndex The index of the line that will be replaced.
     * @param newLine The line that will be replaced with the old one.
     */
    public void editLine(int lineIndex, String newLine) {
        ArrayList<String> lines = readAllLines();
        List<String> list = lines.stream().map(l -> l.equals(lines.get(lineIndex)) ? newLine : l).toList();
        writeToFile(list);
    }

    /**
     * @param lineIndex The index of the line that will be removed.
     */
    public void removeLine(int lineIndex) {
        ArrayList<String> l = readAllLines();
        l.remove(lineIndex);
        writeToFile(l);
    }

    /**
     * @param line The line that will be removed.
     */
    public void removeLine(String line) {
        ArrayList<String> l = readAllLines();
        l.remove(line);
        writeToFile(l);
    }

    /**
     * @param l The line that will go to the end of the lines.
     */
    public void appendLine(String l) {
        ArrayList<String> lines = readAllLines();
        lines.add(l);
        writeToFile(lines);
    }

    /**
     * @param l The line that'll go before the line in that index
     * @param index The index where you want to put the line.
     */
    public void appendLine(String l, int index) {
        ArrayList<String> lines = readAllLines();

        List<String> BEFORE = new ArrayList<>();
        List<String> AFTER = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String obj = lines.get(i);
            if (i < index) {
                // Lines Before
                BEFORE.add(obj);
            } else {
                // Lines After
                AFTER.add(obj);
            }
        }
        BEFORE.add(l);
        List<String> list = Stream.concat(BEFORE.stream(), AFTER.stream()).toList();
        writeToFile(list);
    }
}
