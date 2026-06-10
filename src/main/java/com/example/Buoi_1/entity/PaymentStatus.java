package com.example.Buoi_1.entity;

public enum PaymentStatus {
    UNPAID,          // Mới tạo, chưa cọc
    PARTIALLY_PAID,  // Đã đặt cọc 20%
    PAID,            // Đã thanh toán 100%
    REFUNDED         // Đã hoàn cọc (hủy đơn)
}
