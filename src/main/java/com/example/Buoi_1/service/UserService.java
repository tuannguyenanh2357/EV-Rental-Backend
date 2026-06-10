package com.example.Buoi_1.service;

import com.example.Buoi_1.dto.request.UserCreationRequest;
import com.example.Buoi_1.dto.request.UserUpdateRequest;
import com.example.Buoi_1.exception.AppException;
import com.example.Buoi_1.exception.ErrorCode;
import com.example.Buoi_1.entity.User;
import com.example.Buoi_1.mapper.UserMapper;
import com.example.Buoi_1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.Buoi_1.dto.response.UserResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Lấy danh sách User
    public List<UserResponse> getAllUser(){
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    //  Lấy 1 User
    public UserResponse getByUserId(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // mật khẩu đang là chữ thường
        User user = userMapper.toUser(request);

        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("customer");
        }

        // MÃ HÓA MẬT KHẨU TRƯỚC KHI LƯU!
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Bây giờ mật khẩu đã được biến thành chuỗi
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUserFromRequest(request, existingUser);

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        existingUser = userRepository.save(existingUser);

        return userMapper.toUserResponse(existingUser);
    }

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }


}
