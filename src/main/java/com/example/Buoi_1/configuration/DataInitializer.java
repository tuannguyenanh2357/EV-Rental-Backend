package com.example.Buoi_1.configuration;

import com.example.Buoi_1.entity.Vehicle;
import com.example.Buoi_1.entity.VehicleStatus;
import com.example.Buoi_1.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (vehicleRepository.count() == 0) {
            log.info("Seeding initial vehicle data...");

            Vehicle vf8 = new Vehicle();
            vf8.setName("VF 8 Plus");
            vf8.setBrand("VinFast");
            vf8.setModel("Plus");
            vf8.setColor("Green");
            vf8.setYear(2024);
            vf8.setLicensePlate("29A-999.99");
            vf8.setBatteryKwh(new BigDecimal("82.00"));
            vf8.setRangeKm(420);
            vf8.setChargeType("DC Fast");
            vf8.setPricePerHour(new BigDecimal("50000.00"));
            vf8.setPricePerDay(new BigDecimal("350000.00"));
            vf8.setPricePerWeek(new BigDecimal("2100000.00"));
            vf8.setLocation("Hà Nội");
            vf8.setStatus(VehicleStatus.AVAILABLE);
            vf8.setIsVisible(true);
            vf8.setDescription("VinFast VF 8 Plus - Xe điện thông minh Việt Nam");
            vf8.setImageUrl("http://localhost:8080/uploads/vf8.png");
            vehicleRepository.save(vf8);

            Vehicle model3 = new Vehicle();
            model3.setName("Model 3 Standard");
            model3.setBrand("Tesla");
            model3.setModel("Standard");
            model3.setColor("Blue");
            model3.setYear(2023);
            model3.setLicensePlate("51G-888.88");
            model3.setBatteryKwh(new BigDecimal("60.00"));
            model3.setRangeKm(358);
            model3.setChargeType("Supercharger");
            model3.setPricePerHour(new BigDecimal("70000.00"));
            model3.setPricePerDay(new BigDecimal("500000.00"));
            model3.setPricePerWeek(new BigDecimal("3000000.00"));
            model3.setLocation("TP. Hồ Chí Minh");
            model3.setStatus(VehicleStatus.RENTED);
            model3.setIsVisible(true);
            model3.setDescription("Tesla Model 3 Standard - Trải nghiệm xe điện hàng đầu thế giới");
            model3.setImageUrl("http://localhost:8080/uploads/model3.png");
            vehicleRepository.save(model3);

            Vehicle atto3 = new Vehicle();
            atto3.setName("Atto 3 Premium");
            atto3.setBrand("BYD");
            atto3.setModel("Premium");
            atto3.setColor("Green");
            atto3.setYear(2024);
            atto3.setLicensePlate("43A-777.77");
            atto3.setBatteryKwh(new BigDecimal("60.50"));
            atto3.setRangeKm(480);
            atto3.setChargeType("AC+DC");
            atto3.setPricePerHour(new BigDecimal("60000.00"));
            atto3.setPricePerDay(new BigDecimal("420000.00"));
            atto3.setPricePerWeek(new BigDecimal("2500000.00"));
            atto3.setLocation("Đà Nẵng");
            atto3.setStatus(VehicleStatus.AVAILABLE);
            atto3.setIsVisible(true);
            atto3.setDescription("BYD Atto 3 Premium - Xe điện phong cách, hiện đại");
            atto3.setImageUrl("http://localhost:8080/uploads/atto3.png");
            vehicleRepository.save(atto3);

            log.info("Vehicle data seeded successfully!");
        }
    }
}
