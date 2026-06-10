package com.example.Buoi_1.dto.request;

import com.example.Buoi_1.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentalCreationRequest {

    @NotBlank(message = "Tên khách hàng không được để trống")
    String customerName;

    @NotBlank(message = "Số điện thoại không được để trống")
    String customerPhone;

    String customerAddress;
    String notes;

    @NotBlank(message = "Biển số xe không được để trống")
    String vehicleLicensePlate;

    @NotNull(message = "Ngày nhận xe không được để trống")
    LocalDate rentalDate;

    @NotNull(message = "Ngày trả xe không được để trống")
    LocalDate returnDate;

    /** Giá gốc trước VAT (frontend gửi lên) */
    double basePrice;

    /** Phương thức đặt cọc: BANK_TRANSFER | CASH */
    @NotNull(message = "Phương thức thanh toán không được để trống")
    PaymentMethod paymentMethod;
}
