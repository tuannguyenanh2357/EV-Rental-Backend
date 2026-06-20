package com.example.Buoi_1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargingStationResponse {
    private String id;
    private String uuid;
    private String title;
    private String addressLine1;
    private Double latitude;
    private Double longitude;
    private Boolean isOperational;
    private String usageCost;
}
