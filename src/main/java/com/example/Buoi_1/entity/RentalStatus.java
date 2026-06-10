package com.example.Buoi_1.entity;

public enum RentalStatus {
    PENDING,     // Vừa đặt, chờ admin duyệt
    CONFIRMED,   // Admin xác nhận, đã nhận cọc
    ACTIVE,      // Đang cho thuê xe
    COMPLETED,   // Hoàn tất chuyến đi
    CANCELLED    // Đã huỷ đơn
}
