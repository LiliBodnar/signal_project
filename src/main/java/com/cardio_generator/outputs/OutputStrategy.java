package com.cardio_generator.outputs;

/**
 * This interface gives an outline for outputting patient's data.
 * Different implementations of this interface specify how the data should be output.
 */
public interface OutputStrategy {

    /**
     * Outputs the specified data for a patient.
     *
     * @param patientId The patient's ID. It should be a positive integer.
     * @param timestamp The time of the recording in milliseconds.
     * @param label Describes the type of data (e.g., "heart rate"). It should not be null or empty.
     * @param data The actual data to be output. It should not be null or empty.
     * @throws IllegalArgumentException if any of the parameters are invalid (e.g., null or empty data/label).
     */

    void output(int patientId, long timestamp, String label, String data);
}
