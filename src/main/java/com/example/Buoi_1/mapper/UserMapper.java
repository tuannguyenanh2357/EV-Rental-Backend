package com.example.Buoi_1.mapper;

import com.example.Buoi_1.dto.request.UserCreationRequest;
import com.example.Buoi_1.dto.request.UserUpdateRequest;
import com.example.Buoi_1.dto.response.UserResponse;
import com.example.Buoi_1.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // gọt vỏ map từ User->UserResponse
    UserResponse toUserResponse(User user);

    //tạo mới map từ Request chứa dữ liệu postman sang Entity User
    User toUser(UserCreationRequest request);

    // cập nhật đổ dữ liệu từ request đè lên entity user  đã có sẵn trong DB
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);
}
