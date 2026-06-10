
package com.example.Buoi_1.service.impl;

import com.example.Buoi_1.dto.request.VehicleCreationRequest;
import com.example.Buoi_1.dto.request.VehicleUpdateRequest;
import com.example.Buoi_1.dto.response.VehicleResponse;
import com.example.Buoi_1.entity.Vehicle;
import com.example.Buoi_1.mapper.VehicleMapper;
import com.example.Buoi_1.repository.VehicleRepository;
import com.example.Buoi_1.service.VehicleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    @Override
    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toVehicleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        return vehicleMapper.toVehicleResponse(vehicle);
    }

    @Override
    public VehicleResponse createVehicle(VehicleCreationRequest request) {
        Vehicle vehicle = vehicleMapper.toVehicle(request);
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toVehicleResponse(savedVehicle);
    }

    @Override
    public VehicleResponse updateVehicle(Long id, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        vehicleMapper.updateVehicleFromRequest(request, vehicle);
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toVehicleResponse(updatedVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
