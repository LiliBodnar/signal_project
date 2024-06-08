package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class implements {@link OutputStrategy} interface
 * and writes patient's data into files in a specified directory.
 * Each label corresponds to a different file, and the data is added to the appropriate file.
 */
// Changed class name to UpperCamelCase.
public class FileOutputStrategy implements OutputStrategy {

    /** Base directory, where output files will be stored. */
    // Changed field name to lowerCamelCase, and assigned final to it.
    private final String baseDirectory;

    /**
     * A map that keeps track of the file paths associated with each label.
     * It matches the label (key) to the file path (value).
     */
    // Changed variable name to lowerCamelCase.
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    /**
     * Constructs a {@code FileOutputStrategy} with the specified base directory.
     *
     * @param baseDirectory Base directory, where output files will be stored. It should not be null or empty.
     */
    /*Changed constructor name to UpperCamelCase to match class name.
    * Moreover, removed the blank line as it is unnecessary.*/
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs the specified data for a patient by writing it to a file.
     * Each label corresponds to a different file in the base directory, and data is added to the appropriate file.
     *
     * @param patientId The ID of the patient. It should be a positive integer.
     * @param timestamp The time of the recording in milliseconds.
     * @param label Describes the type of data (e.g., "heart rate"). It should not be null or empty.
     * @param data The actual data to be output. It should not be null or empty.
     * @throws IllegalArgumentException if any of the parameters are invalid (e.g., null or empty data/label).
     */
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