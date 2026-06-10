package com.example.Buoi_1.mapper;

import com.example.Buoi_1.dto.request.RentalUpdateRequest;
import com.example.Buoi_1.dto.response.RentalResponse;
import com.example.Buoi_1.entity.Rental;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface RentalMapper {

    /**
     * Entity → Response DTO
     * Lưu ý: qrCodeUrl không có trong entity,
     * được set thủ công trong Service sau khi map.
     */
    @Mapping(target = "qrCodeUrl", ignore = true)
    RentalResponse toRentalResponse(Rental rental);

    /**
     * UpdateRequest → Entity (partial update, bỏ qua null)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rentalCode", ignore = true)
    // allow mapping rentalStatus and paymentStatus
    @Mapping(target = "paymentMethod", ignore = true)
    @Mapping(target = "basePrice", ignore = true)
    @Mapping(target = "vatAmount", ignore = true)
    @Mapping(target = "depositAmount", ignore = true)
    @Mapping(target = "remainingAmount", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "depositPaidAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRentalFromRequest(RentalUpdateRequest request, @MappingTarget Rental rental);
}
