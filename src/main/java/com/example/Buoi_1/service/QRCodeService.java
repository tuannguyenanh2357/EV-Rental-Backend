package com.example.Buoi_1.service;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Sinh QR code chuyển khoản qua VietQR API (public, không cần đăng ký)
 * Tài khoản: 0388265317 - Nguyễn Anh Tuấn - MBBANK
 */
@Service
public class QRCodeService {

    private static final String BANK_ID      = "MB";           // Mã ngân hàng VietQR
    private static final String ACCOUNT_NO   = "0388265317";   // Số tài khoản
    private static final String ACCOUNT_NAME = "NGUYEN ANH TUAN"; // Tên tài khoản (không dấu)
    private static final String TEMPLATE     = "compact2";     // Kiểu QR: compact2 | qr_only | print

    /**
     * Sinh URL ảnh QR VietQR cho đơn thuê xe
     *
     * @param rentalCode  Mã đơn, VD: EV-20240608-12345
     * @param amount      Số tiền cọc (VND), VD: 108000
     * @return URL ảnh QR có thể dùng trực tiếp trong <img src="...">
     */
    public String generateQRUrl(String rentalCode, double amount) {
        long amountLong = Math.round(amount);

        // Nội dung chuyển khoản: "COC EV-20240608-12345"
        String transferContent = "COC " + rentalCode;

        String encodedContent = URLEncoder.encode(transferContent, StandardCharsets.UTF_8);
        String encodedName    = URLEncoder.encode(ACCOUNT_NAME, StandardCharsets.UTF_8);

        return String.format(
            "https://img.vietqr.io/image/%s-%s-%s.png?amount=%d&addInfo=%s&accountName=%s",
            BANK_ID,
            ACCOUNT_NO,
            TEMPLATE,
            amountLong,
            encodedContent,
            encodedName
        );
    }
}
