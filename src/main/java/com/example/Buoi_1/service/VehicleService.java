package com.example.Buoi_1.service;

import com.example.Buoi_1.dto.request.VehicleCreationRequest;
import com.example.Buoi_1.dto.request.VehicleUpdateRequest;
import com.example.Buoi_1.dto.response.VehicleResponse;
import com.example.Buoi_1.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<VehicleResponse> getAllVehicles();

    VehicleResponse getVehicleById(Long id);

    VehicleResponse createVehicle(VehicleCreationRequest request);

    VehicleResponse updateVehicle(Long id, VehicleUpdateRequest request);

    void deleteVehicle(Long id);
}
