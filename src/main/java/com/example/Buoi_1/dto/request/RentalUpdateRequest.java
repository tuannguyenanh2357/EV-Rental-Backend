package com.example.Buoi_1.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalUpdateRequest {
    String customerName;
    String customerPhone;
    String customerAddress;
    String vehicleLicensePlate;
    LocalDate rentalDate;
    LocalDate returnDate;
    Double totalPrice;
    com.example.Buoi_1.entity.RentalStatus rentalStatus;
    com.example.Buoi_1.entity.PaymentStatus paymentStatus;
}
