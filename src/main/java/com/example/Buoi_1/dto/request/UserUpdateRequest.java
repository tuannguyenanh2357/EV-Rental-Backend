package com.example.Buoi_1.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserUpdateRequest {
    @Size(min = 3, message = "Username phải ít nhất 3 ký tự")
    String username;
    
    String firstname;
    String lastname;
    
    @Min(value = 1, message = "Tuổi phải lớn hơn 0")
    Integer age;
    
    @Email(message = "Email không hợp lệ")
    String email;
    
    @Size(min = 8, message = "Password ít nhất phải có 8 chữ số")
    String password;

    String role;
    
    String phone;
    String address;
    String avatar;
}
