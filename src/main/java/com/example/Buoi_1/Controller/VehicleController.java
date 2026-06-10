package com.example.Buoi_1.Controller;

import com.example.Buoi_1.dto.request.ApiResponse;
import com.example.Buoi_1.dto.request.VehicleCreationRequest;
import com.example.Buoi_1.dto.request.VehicleUpdateRequest;
import com.example.Buoi_1.dto.response.VehicleResponse;
import com.example.Buoi_1.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;   
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }
            String uploadPath = new File("uploads").getAbsolutePath();
            if (!new File(uploadPath + "/vf8.png").exists()) {
                File subDir = new File("./Buoi_1/uploads");
                if (subDir.exists()) {
                    uploadPath = subDir.getAbsolutePath();
                }
            }
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll("\\s+", "_");
            File destination = new File(uploadDir, fileName);
            file.transferTo(destination);

            String fileUrl = "http://localhost:8080/uploads/" + fileName;
            
            ApiResponse<String> apiResponse = new ApiResponse<>();
            apiResponse.setData(fileUrl);
            apiResponse.setMessage("Upload ảnh thành công");
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<>();
            apiResponse.setCode(400);
            apiResponse.setMessage("Upload ảnh thất bại: " + e.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleResponse>>> getAllVehicles() {
        ApiResponse<List<VehicleResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(vehicleService.getAllVehicles());
        apiResponse.setMessage("Lấy danh sách xe thành công");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> getVehicleById(@PathVariable Long id) {
        ApiResponse<VehicleResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(vehicleService.getVehicleById(id));
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(
            @Valid @RequestBody VehicleCreationRequest request) {
        ApiResponse<VehicleResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(vehicleService.createVehicle(request));
        apiResponse.setMessage("Tạo xe thành công");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponse>> updateVehicle(@PathVariable Long id,
            @Valid @RequestBody VehicleUpdateRequest request) {
        ApiResponse<VehicleResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(vehicleService.updateVehicle(id, request));
        apiResponse.setMessage("Cập nhật xe thành công");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Đã xoá xe thành công");
        return ResponseEntity.ok(apiResponse);
    }
}
