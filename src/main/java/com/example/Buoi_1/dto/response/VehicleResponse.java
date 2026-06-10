package com.example.Buoi_1.dto.response;

import com.example.Buoi_1.entity.VehicleStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleResponse {

    Long id;

    String name;

    String brand;

    String model;

    String color;

    Integer year;

    String licensePlate;

    BigDecimal batteryKwh;

    Integer rangeKm;

    String chargeType;

    BigDecimal pricePerHour;

    BigDecimal pricePerDay;

    BigDecimal pricePerWeek;

    String location;

    VehicleStatus status;

    Boolean isVisible;

    String description;

    String imageUrl;

    LocalDateTime createdAt;
}