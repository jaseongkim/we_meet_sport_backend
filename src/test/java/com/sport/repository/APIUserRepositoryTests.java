package com.sport.repository;

import com.sport.domain.APIUser;
import com.sport.domain.APIUserRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class APIUserRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private APIUserRepository apiUserRepository;

    @Test
    public void testInserts() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            APIUser apiUser = APIUser.builder()
                    .email("apiuser" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .build();
            apiUser.addRole(APIUserRole.USER);

            apiUserRepository.save(apiUser);
        });
    }
}
