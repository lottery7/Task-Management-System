package dev.lottery.tms.mapper;

import dev.lottery.tms.dto.request.CreateUserRequest;
import dev.lottery.tms.dto.response.UserResponse;
import dev.lottery.tms.entity.Role;
import dev.lottery.tms.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", imports = {Set.class, Role.class})
public interface UserMapper {
    @Mapping(target = "roles", expression = "java(Set.of(Role.USER))")
    User toUser(CreateUserRequest createUserRequest);

    UserResponse toUserResponse(User user);
}
