package com.example.Buoi_1.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
     Long id;
     String username;
     String firstname;
     String lastname;
     Integer age;
     String email;
     String role;
     String phone;
     String address;
     String avatar;
}
