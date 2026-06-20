package com.example.Buoi_1.Controller;

import com.example.Buoi_1.dto.request.ApiResponse;
import com.example.Buoi_1.dto.response.ChargingStationResponse;
import com.example.Buoi_1.service.ChargingStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChargingStationController {

    private final ChargingStationService chargingStationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChargingStationResponse>>> getStations() {
        ApiResponse<List<ChargingStationResponse>> apiResponse = new ApiResponse<>();
        try {
            List<ChargingStationResponse> stations = chargingStationService.getChargingStations();
            apiResponse.setData(stations);
            apiResponse.setMessage("Lấy danh sách trạm sạc thành công");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            apiResponse.setCode(400);
            apiResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
}
