package com.sport.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class APIUserRepositoryTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private APIUserRepository apiUserRepository;

//    @Test
//    public void testInserts() {
//        IntStream.rangeClosed(1, 10).forEach(i -> {
//            APIUser apiUser = APIUser.builder()
//                    .email("apiuser" + i)
//                    .password(passwordEncoder.encode("1111"))
//                    .build();
//            apiUser.addRole(APIUserRole.USER);
//
//            apiUserRepository.save(apiUser);
//        });
//    }
}
