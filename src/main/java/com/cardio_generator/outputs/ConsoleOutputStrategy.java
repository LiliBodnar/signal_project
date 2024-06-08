package com.cardio_generator.outputs;

/**
 * This class implements the {@link OutputStrategy} interface and prints patient's data onto the console.
 */
public class ConsoleOutputStrategy implements OutputStrategy {
    /**
     * Outputs the specified data for a patient by printing it to the console.
     *
     * @param patientId The ID of the patient. It should be a positive integer.
     * @param timestamp The time of the recording in milliseconds.
     * @param label Describes the type of data (e.g., "heart rate"). It should not be null or empty.
     * @param data The actual data to be output. It should not be null or empty.
     * @throws IllegalArgumentException if any of the parameters are invalid (e.g., null or empty data/label).
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
    }
}
