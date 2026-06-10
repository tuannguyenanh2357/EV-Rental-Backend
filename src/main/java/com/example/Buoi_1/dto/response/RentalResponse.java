package com.example.Buoi_1.dto.response;

import com.example.Buoi_1.entity.PaymentMethod;
import com.example.Buoi_1.entity.PaymentStatus;
import com.example.Buoi_1.entity.RentalStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalResponse {

    Long id;
    String customerName;
    String customerPhone;
    String customerAddress;
    String notes;
    String vehicleLicensePlate;

    LocalDate rentalDate;
    LocalDate returnDate;

    // Giá tiền chi tiết
    double basePrice;
    double vatAmount;
    double totalPrice;
    double depositAmount;
    double remainingAmount;

    // Mã đơn & trạng thái
    String rentalCode;
    RentalStatus rentalStatus;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;

    // QR URL — chỉ có giá trị khi paymentMethod = BANK_TRANSFER
    String qrCodeUrl;

    LocalDateTime createdAt;
    LocalDateTime depositPaidAt;
}
