package com.sport.controller;

import com.sport.dto.AlarmDTO;
import com.sport.dto.AlarmStatusDTO;
import com.sport.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;


@RestController
@Log4j2
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping("/api")
    public Map<String, Object> apply(@RequestBody AlarmDTO alarmDTO , Principal principal) {

        Map<String, Object> map = null;
        String email = principal.getName();

        alarmDTO.setApplicant(email);

        map = alarmService.apply(alarmDTO);

        return map;
    }

    @GetMapping("/api")
    public Map<String, Object> list(Principal principal){
        Map<String, Object> map = null;

        String email = principal.getName();

        map = alarmService.list(email);

        return map;
    }

    @PostMapping("/api/status")
    public Map<String, Object> status(@RequestBody AlarmStatusDTO AlarmStatusDTO, Principal principal) {

        Map<String, Object> map = null;
        String email = principal.getName();

        AlarmStatusDTO.setEmail(email);



        return map;
    }





}
