package com.example.Buoi_1.Controller;

import com.example.Buoi_1.dto.request.RentalCreationRequest;
import com.example.Buoi_1.dto.request.RentalUpdateRequest;
import com.example.Buoi_1.dto.response.RentalResponse;
import com.example.Buoi_1.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    // ─────────── CRUD ───────────

    @GetMapping
    public ResponseEntity<List<RentalResponse>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getRentalById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @PostMapping
    public ResponseEntity<RentalResponse> createRental(
            @Valid @RequestBody RentalCreationRequest request) {
        return ResponseEntity.ok(rentalService.createRental(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalResponse> updateRental(
            @PathVariable Long id,
            @Valid @RequestBody RentalUpdateRequest request) {
        return ResponseEntity.ok(rentalService.updateRental(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────── Admin: Xác nhận thanh toán ───────────

    /**
     * Admin xác nhận đã nhận cọc 20%
     * PATCH /api/rentals/{id}/confirm-deposit
     * → PaymentStatus = PARTIALLY_PAID, RentalStatus = CONFIRMED
     */
    @PatchMapping("/{id}/confirm-deposit")
    public ResponseEntity<RentalResponse> confirmDeposit(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.confirmDeposit(id));
    }

    /**
     * Admin xác nhận khách thanh toán đầy đủ
     * PATCH /api/rentals/{id}/confirm-full-payment
     * → PaymentStatus = PAID
     */
    @PatchMapping("/{id}/confirm-full-payment")
    public ResponseEntity<RentalResponse> confirmFullPayment(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.confirmFullPayment(id));
    }
}
