package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This interface gives an outline for generating patient's data.
 * Different implementations of this interface provide methods to generate different types of data for a patient
 * and to output it using a specified {@link OutputStrategy}.
 */
public interface PatientDataGenerator {
    /**
     * Generates data for a given patient and outputs it using a given {@link OutputStrategy}.
     *
     * @param patientId The patient's ID. It should be a positive integer.
     * @param outputStrategy The specified way to output the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
