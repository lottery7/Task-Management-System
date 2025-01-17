package dev.lottery.tms.util;

import dev.lottery.tms.entity.Role;
import dev.lottery.tms.security.impl.JwtAuthentication;
import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUtils {
    public static JwtAuthentication generateAuth(Claims claims) {
        JwtAuthentication authentication = new JwtAuthentication();
        authentication.setRoles(getRoles(claims));
        authentication.setName(claims.get("name", String.class));
        authentication.setEmail(claims.getSubject());
        return authentication;
    }

    private static Set<Role> getRoles(Claims claims) {
        List<String> rolesStr = claims.get("roles", List.class);
        return rolesStr.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}