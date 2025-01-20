package dev.lottery.tms;

import dev.lottery.tms.entity.User;
import dev.lottery.tms.model.Role;
import dev.lottery.tms.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class TestUsersDataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void registerUser(String name, String email, String password, Set<Role> roles) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);

        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    private void registerTestUser() {
        registerUser("TEST_USER",
                "test_user@email.com",
                "TEST_USER's password",
                Set.of(Role.USER)
        );
    }

    private void registerEugeneUser() {
        registerUser("Eugene Akimov",
                "eugene@email.com",
                "Eugene's password",
                Set.of(Role.USER)
        );
    }

    private void registerTestAdmin() {
        registerUser("TEST_ADMIN",
                "test_admin@email.com",
                "TEST_ADMIN's password",
                Set.of(Role.ADMIN)
        );
    }

    @Override
    public void run(ApplicationArguments args) {
        registerTestUser();
        registerTestAdmin();
        registerEugeneUser();
    }
}
