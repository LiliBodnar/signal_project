package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

// Changed class name to UpperCamelCase.
public class FileOutputStrategy implements OutputStrategy {

    // Changed field name to lowerCamelCase, and assigned final to it.
    private final String baseDirectory;

    // Changed variable name to lowerCamelCase.
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    /*Changed constructor name to UpperCamelCase to match class name.
    * Moreover, removed the blank line as it is unnecessary.*/
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        // Changed variable name to lowerCamelCase.
        String filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (IOException e) {   // Changed Exception to IOException to be more specific and avoid over generalizing.
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}