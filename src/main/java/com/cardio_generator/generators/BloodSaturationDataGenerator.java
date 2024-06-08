package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This class implements the {@link PatientDataGenerator} interface
 * and generates blood saturation data for patients. It simulates realistic blood saturation values and
 * outputs the data using a specified {@link OutputStrategy}.
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    /** A random number generator to generate different blood saturation values. */
    private static final Random random = new Random();
    /** An array storing the last saturation values for each patient. */
    private int[] lastSaturationValues;

    /**
     * Constructs a {@code BloodSaturationDataGenerator} for a given number of patients.
     * Initializes each patient's blood saturation level to a default value between 95 and 100.
     *
     * @param patientCount The number of patients whose data will be generated. It should be a positive integer.
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * Generates blood saturation data for a given patient and outputs it using a given {@link OutputStrategy}.
     * The blood saturation values vary between 90 and 100.
     *
     * @param patientId The patient's ID. It should be a positive integer.
     * @param outputStrategy The specified way to output the generated data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
