package com.example.Buoi_1.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "Username must be at least 3 characters")
     String username;

     String firstname;
     String lastname;

    @NotNull(message = "Tuổi không được null")
    @Min(value = 1, message = "Tuổi phải lớn hơn 0")
     Integer age;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
     String email;

    @NotNull(message = "Password không được để trống")
    @Size(min = 8, message = "Password ít nhất phải có 8 chữ số")
     String password;

     String role;
     String phone;
     String address;
     String avatar;
}
