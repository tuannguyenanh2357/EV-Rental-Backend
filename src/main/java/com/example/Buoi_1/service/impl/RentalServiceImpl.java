package com.example.Buoi_1.service.impl;

import com.example.Buoi_1.dto.request.RentalCreationRequest;
import com.example.Buoi_1.dto.request.RentalUpdateRequest;
import com.example.Buoi_1.dto.response.RentalResponse;
import com.example.Buoi_1.entity.*;
import com.example.Buoi_1.mapper.RentalMapper;
import com.example.Buoi_1.repository.RentalRepository;
import com.example.Buoi_1.service.QRCodeService;
import com.example.Buoi_1.service.RentalService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RentalServiceImpl implements RentalService {

    RentalRepository rentalRepository;
    RentalMapper rentalMapper;
    QRCodeService qrCodeService;

    // ───────────────────────────── READ ─────────────────────────────

    @Override
    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(this::toResponseWithQR)
                .collect(Collectors.toList());
    }

    @Override
    public RentalResponse getRentalById(Long id) {
        Rental rental = findById(id);
        return toResponseWithQR(rental);
    }

    // ───────────────────────────── CREATE ────────────────────────────

    @Override
    public RentalResponse createRental(RentalCreationRequest request) {
        // 1. Tính tiền (server-side, không tin tưởng client)
        double basePrice     = request.getBasePrice();
        double vatAmount     = Math.round(basePrice * 0.08);
        double totalPrice    = basePrice + vatAmount;
        double depositAmount = Math.round(totalPrice * 0.20);
        double remaining     = totalPrice - depositAmount;

        // 2. Sinh mã đơn duy nhất: EV-YYYYMMDD-millis
        String rentalCode = generateRentalCode();

        // 3. Build entity
        Rental rental = Rental.builder()
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .customerAddress(request.getCustomerAddress())
                .notes(request.getNotes())
                .vehicleLicensePlate(request.getVehicleLicensePlate())
                .rentalDate(request.getRentalDate())
                .returnDate(request.getReturnDate())
                .paymentMethod(request.getPaymentMethod())
                .rentalCode(rentalCode)
                .rentalStatus(RentalStatus.PENDING)
                .paymentStatus(PaymentStatus.UNPAID)
                .basePrice(basePrice)
                .vatAmount(vatAmount)
                .totalPrice(totalPrice)
                .depositAmount(depositAmount)
                .remainingAmount(remaining)
                .createdAt(LocalDateTime.now())
                .build();

        Rental saved = rentalRepository.save(rental);
        return toResponseWithQR(saved);
    }

    // ───────────────────────────── UPDATE ────────────────────────────

    @Override
    public RentalResponse updateRental(Long id, RentalUpdateRequest request) {
        Rental rental = findById(id);
        rentalMapper.updateRentalFromRequest(request, rental);
        return toResponseWithQR(rentalRepository.save(rental));
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    // ─────────────────── PAYMENT CONFIRM (Admin) ─────────────────────

    /**
     * Admin xác nhận đã nhận tiền cọc.
     * → PaymentStatus = PARTIALLY_PAID
     * → RentalStatus  = CONFIRMED
     */
    @Override
    public RentalResponse confirmDeposit(Long id) {
        Rental rental = findById(id);
        rental.setPaymentStatus(PaymentStatus.PARTIALLY_PAID);
        rental.setRentalStatus(RentalStatus.CONFIRMED);
        rental.setDepositPaidAt(LocalDateTime.now());
        return toResponseWithQR(rentalRepository.save(rental));
    }

    /**
     * Admin xác nhận khách đã thanh toán toàn bộ.
     * → PaymentStatus = PAID
     */
    @Override
    public RentalResponse confirmFullPayment(Long id) {
        Rental rental = findById(id);
        rental.setPaymentStatus(PaymentStatus.PAID);
        return toResponseWithQR(rentalRepository.save(rental));
    }

    // ─────────────────────────── HELPERS ─────────────────────────────

    /** Sinh mã đơn: EV-YYYYMMDD-XXXXX */
    private String generateRentalCode() {
        String date   = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String suffix = String.valueOf(System.currentTimeMillis()).substring(8);
        return "EV-" + date + "-" + suffix;
    }

    /** Map entity → response và gắn qrCodeUrl nếu BANK_TRANSFER */
    private RentalResponse toResponseWithQR(Rental rental) {
        RentalResponse response = rentalMapper.toRentalResponse(rental);
        if (rental.getPaymentMethod() == PaymentMethod.BANK_TRANSFER
                && rental.getRentalCode() != null) {
            response.setQrCodeUrl(
                qrCodeService.generateQRUrl(rental.getRentalCode(), rental.getDepositAmount())
            );
        }
        return response;
    }

    private Rental findById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuê xe với id: " + id));
    }
}
