package com.example.Buoi_1.service;

import com.example.Buoi_1.dto.response.ChargingStationResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChargingStationService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChargingStationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${openchargemap.api.url}")
    private String apiUrl;

    @Value("${openchargemap.api.key}")
    private String apiKey;

    public List<ChargingStationResponse> getChargingStations() {
        List<ChargingStationResponse> stations = new ArrayList<>();
        try {
            String url = apiUrl + "?output=json&countrycode=VN&maxresults=100&key=" + apiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                if (rootNode.isArray()) {
                    for (JsonNode node : rootNode) {
                        ChargingStationResponse station = new ChargingStationResponse();
                        
                        station.setId(node.path("ID").asText(""));
                        station.setUuid(node.path("UUID").asText(""));
                        station.setUsageCost(node.path("UsageCost").asText(""));
                        
                        JsonNode addressInfo = node.path("AddressInfo");
                        if (!addressInfo.isMissingNode()) {
                            station.setTitle(addressInfo.path("Title").asText(""));
                            station.setAddressLine1(addressInfo.path("AddressLine1").asText(""));
                            station.setLatitude(addressInfo.path("Latitude").asDouble(0.0));
                            station.setLongitude(addressInfo.path("Longitude").asDouble(0.0));
                        }
                        
                        JsonNode statusType = node.path("StatusType");
                        if (!statusType.isMissingNode()) {
                            station.setIsOperational(statusType.path("IsOperational").asBoolean(false));
                        }
                        
                        stations.add(station);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error fetching charging stations from OpenChargeMap", e);
            throw new RuntimeException("Lỗi khi lấy dữ liệu trạm sạc: " + e.getMessage());
        }
        return stations;
    }
}
