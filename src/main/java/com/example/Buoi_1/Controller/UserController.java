package com.example.Buoi_1.Controller;

import com.example.Buoi_1.dto.request.ApiResponse;
import com.example.Buoi_1.dto.request.UserCreationRequest;
import com.example.Buoi_1.dto.request.UserUpdateRequest;
import com.example.Buoi_1.dto.response.UserResponse;
import com.example.Buoi_1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers() {
       ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
       apiResponse.setData(userService.getAllUser());
       apiResponse.setMessage("Lấy danh sách thành công");

        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.getByUserId(id));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/myInfo")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo() {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.getMyInfo());
        apiResponse.setMessage("Lấy thông tin thành công");
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.createUser(request));
        apiResponse.setMessage("Tạo mới User thành công");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id,
                                           @Valid @RequestBody UserUpdateRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.updateUser(id, request));
        apiResponse.setMessage("Cập nhật thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Đã xoá thành công");
        return ResponseEntity.ok(apiResponse);
    }

}
