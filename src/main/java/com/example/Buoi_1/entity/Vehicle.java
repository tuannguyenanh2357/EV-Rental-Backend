package com.example.Buoi_1.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 200)
    String name;

    @Column(length = 100)
    String brand; // Đã sửa từ branch -> brand (VinFast, Tesla...)

    @Column(length = 100)
    String model;

    @Column(length = 50)
    String color;

    Integer year;

    @Column(name = "license_plate", length = 50)
    String licensePlate;

    @Column(name = "battery_kwh", precision = 10, scale = 2)
    BigDecimal batteryKwh; // Dung lượng pin (kWh)

    @Column(name = "range_km")
    Integer rangeKm; // Phạm vi di chuyển (km)

    @Column(name = "charge_type", length = 50)
    String chargeType; // Loại sạc (AC/DC/FastCharge)

    @Column(name = "price_per_hour", precision = 10, scale = 2)
    BigDecimal pricePerHour;

    @Column(name = "price_per_day", precision = 10, scale = 2)
    BigDecimal pricePerDay;

    @Column(name = "price_per_week", precision = 10, scale = 2)
    BigDecimal pricePerWeek;

    @Column(length = 200)
    String location;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('AVAILABLE', 'RENTED', 'MAINTENANCE') DEFAULT 'AVAILABLE'")
    VehicleStatus status = VehicleStatus.AVAILABLE;

    @Column(name = "is_visible")
    Boolean isVisible = true; // Phục vụ soft delete (ẩn/hiện xe)

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(name = "image_url", columnDefinition = "TEXT")
    String imageUrl;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}