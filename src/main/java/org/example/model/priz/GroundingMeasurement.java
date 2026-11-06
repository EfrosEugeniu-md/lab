package org.example.model.priz;


public record GroundingMeasurement(
        String id,
        String soil,
        String moisture,
        String voltage,
        String soilCoefficient,
        String groundingPurpose,
        String target,
        String depth,
        String resistance,
        String groundingType,
        String size,
        String thickness
) {
}

