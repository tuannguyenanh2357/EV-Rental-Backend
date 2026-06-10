package com.example.Buoi_1.dto.request;

import com.example.Buoi_1.entity.VehicleStatus;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleCreationRequest {

    @NotBlank(message = "Vehicle name cannot be empty")
    @Size(max = 200, message = "Vehicle name must not exceed 200 characters")
    String name;

    @Size(max = 100, message = "Brand must not exceed 100 characters")
    String brand;

    @Size(max = 100, message = "Model must not exceed 100 characters")
    String model;

    @Size(max = 50, message = "Color must not exceed 50 characters")
    String color;

    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    @Max(value = 2100, message = "Year is invalid")
    Integer year;

    @Size(max = 50, message = "License plate must not exceed 50 characters")
    String licensePlate;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "Battery capacity must be greater than 0")
    BigDecimal batteryKwh;

    @Min(value = 0, message = "Range cannot be negative")
    Integer rangeKm;

    @Size(max = 50, message = "Charge type must not exceed 50 characters")
    String chargeType;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "Price per hour must be greater than 0")
    BigDecimal pricePerHour;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "Price per day must be greater than 0")
    BigDecimal pricePerDay;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "Price per week must be greater than 0")
    BigDecimal pricePerWeek;

    @Size(max = 200, message = "Location must not exceed 200 characters")
    String location;

    VehicleStatus status;

    Boolean isVisible;

    String description;

    String imageUrl;
}