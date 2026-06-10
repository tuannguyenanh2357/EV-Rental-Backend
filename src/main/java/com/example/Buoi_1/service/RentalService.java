package com.example.Buoi_1.service;

import com.example.Buoi_1.dto.request.RentalCreationRequest;
import com.example.Buoi_1.dto.request.RentalUpdateRequest;
import com.example.Buoi_1.dto.response.RentalResponse;

import java.util.List;

public interface RentalService {

    List<RentalResponse> getAllRentals();

    RentalResponse getRentalById(Long id);

    RentalResponse createRental(RentalCreationRequest request);

    RentalResponse updateRental(Long id, RentalUpdateRequest request);

    void deleteRental(Long id);

    /** Admin xác nhận đã nhận cọc → PaymentStatus = PARTIALLY_PAID, RentalStatus = CONFIRMED */
    RentalResponse confirmDeposit(Long id);

    /** Admin xác nhận đã nhận đủ tiền → PaymentStatus = PAID */
    RentalResponse confirmFullPayment(Long id);
}
