package com.example.Buoi_1.mapper;

import com.example.Buoi_1.dto.request.VehicleCreationRequest;
import com.example.Buoi_1.dto.request.VehicleUpdateRequest;
import com.example.Buoi_1.dto.response.VehicleResponse;
import com.example.Buoi_1.entity.Vehicle;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VehicleMapper {    

    VehicleResponse toVehicleResponse(Vehicle vehicle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Vehicle toVehicle(VehicleCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVehicleFromRequest(VehicleUpdateRequest request, @MappingTarget Vehicle vehicle);
}
