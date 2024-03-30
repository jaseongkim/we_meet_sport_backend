package com.sport.controller;

import com.sport.dto.APIUserJoinDTO;
import com.sport.service.APIUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("test")
    public Map<String, String> test(){

        Map<String, String> map = new HashMap<>();

        map.put("테스트", "테스트");

        return map;

    }



}
