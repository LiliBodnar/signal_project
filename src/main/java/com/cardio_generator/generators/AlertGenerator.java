package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy; // no blank lines between import statements

public class AlertGenerator implements PatientDataGenerator {

    // Changed constant name to UPPER_SNAKE_CASE.
    public static final Random RANDOM_GENERATOR = new Random();
    // Changed variable name to lowerCamelCase and made it final.
    private final boolean[] alertStates; // false = resolved, true = pressed

    // Line break after the opening brace and line break before the closing brace.
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
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