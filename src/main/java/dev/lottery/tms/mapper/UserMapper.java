package dev.lottery.tms.mapper;

import dev.lottery.tms.dto.request.RegisterRequest;
import dev.lottery.tms.dto.response.UserResponse;
import dev.lottery.tms.entity.User;
import dev.lottery.tms.model.Role;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Mapper(componentModel = "spring", imports = {Set.class, Role.class})
public abstract class UserMapper {
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @AfterMapping
    protected void hashPassword(RegisterRequest registerRequest, @MappingTarget User user) {
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(hashedPassword);
    }

    @Mapping(target = "roles", expression = "java(Set.of(Role.USER))")
    @Mapping(target = "password", ignore = true)
    public abstract User toUser(RegisterRequest registerRequest);

    public abstract UserResponse toUserResponse(User user);
}
