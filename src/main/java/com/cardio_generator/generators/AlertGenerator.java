package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy; // no blank lines between import statements

/**
 * This class implements the {@link PatientDataGenerator} interface
 * and simulates alerts for patients. Alerts can be triggered and resolved based on random probabilities.
 */
public class AlertGenerator implements PatientDataGenerator {

    /** A random number generator to simulate alerts. */
    // Changed constant name to UPPER_SNAKE_CASE.
    public static final Random RANDOM_GENERATOR = new Random();
    /** An array representing the alert-state of each patient. False means that the alert is resolved,
     * and true means that the alert was pressed, so it is active at the moment. */
    // Changed variable name to lowerCamelCase and made it final.
    private final boolean[] alertStates; // false = resolved, true = active

    /**
     * Constructs an {@code AlertGenerator} for a given number of patients.
     * Initializes each patient's alert state to a default resolved (false) state.
     *
     * @param patientCount The number of patients whose data will be generated. It should be a positive integer.
     */
    // Line break after the opening brace and line break before the closing brace.
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alert data for a specified patient and outputs it using the provided {@link OutputStrategy}.
     * Alerts are randomly triggered and resolved based on specified probabilities.
     *
     * @param patientId The patient's ID. It should be a positive integer.
     * @param outputStrategy The specified way to output the generated data.
     * @throws ArrayIndexOutOfBoundsException if the patientId is out of bounds for the alertStates array.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) throws RuntimeException {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Changed variable name to lowerCamelCase.
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) { // Changed error handling to be more specific and differentiate between invalid parameter and runtime errors.
            System.err.println("Invalid patient ID: " + patientId);
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("A runtime error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}