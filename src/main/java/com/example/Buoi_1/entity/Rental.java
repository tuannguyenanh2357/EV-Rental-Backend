package com.example.Buoi_1.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rentals")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===== Thông tin khách hàng =====
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String vehicleLicensePlate;
    private String notes;

    // ===== Thời gian thuê =====
    private LocalDate rentalDate;
    private LocalDate returnDate;

    // ===== Giá tiền =====
    private double basePrice;       // Giá gốc trước VAT
    private double vatAmount;       // VAT 8%
    private double totalPrice;      // Tổng = basePrice + vatAmount
    private double depositAmount;   // Cọc = totalPrice × 20%
    private double remainingAmount; // Còn lại = totalPrice - depositAmount

    // ===== Mã đơn (dùng trong QR) =====
    @Column(unique = true)
    private String rentalCode;

    // ===== Trạng thái đơn thuê =====
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private RentalStatus rentalStatus = RentalStatus.PENDING;

    // ===== Phương thức đặt cọc =====
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // ===== Trạng thái thanh toán =====
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    // ===== Thời điểm =====
    private LocalDateTime createdAt;
    private LocalDateTime depositPaidAt;
}
