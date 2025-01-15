package dev.lottery.tms.mapper;

import dev.lottery.tms.dto.CreateUserRequest;
import dev.lottery.tms.dto.UserResponse;
import dev.lottery.tms.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(CreateUserRequest createUserRequest);

    UserResponse toUserResponse(User user);
}
