package com.sport.controller;

import com.sport.dto.APIUserDTO;
import com.sport.dto.APIUserJoinDTO;
import com.sport.service.APIUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/member")
@RequiredArgsConstructor
public class APIUserController {

    private final APIUserService apiUserService;

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody APIUserJoinDTO apiUserJoinDTO) {

        Map<String, String>  map = null;

        log.info(apiUserJoinDTO);

        map = apiUserService.signup(apiUserJoinDTO);

        return map;
    }



    @GetMapping("/api/user")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public Map<String, String> test(){

        Map<String, String> map = new HashMap<>();

        map.put("테스트", "테스트");

        return map;
    }

    @GetMapping("/api/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Map<String, String> test2(){

        Map<String, String> map = new HashMap<>();

        map.put("테스트", "테스트");

        return map;
    }

    @GetMapping("/api/test")
    public Map<String, String> test3(Authentication authentication, @RequestParam("test") String test){


        Object principal = authentication.getPrincipal();

        if (principal instanceof APIUserDTO) {
            // principal 객체를 APIUserDTO 타입으로 캐스팅
            APIUserDTO apiUserDTO = (APIUserDTO) principal;
            log.info(apiUserDTO.getNickName());

            Map<String, String> map = new HashMap<>();
            map.put("nickName", apiUserDTO.getNickName());
            map.put("test", test);
            return map;
        } else {
            // principal 객체가 APIUserDTO 타입이 아닌 경우의 처리
            log.info("UserDetails is not an instance of APIUserDTO");
            return Collections.singletonMap("error", "Authentication principal is not of type APIUserDTO");
        }
    }



}
