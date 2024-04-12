package com.sport.controller;

import com.sport.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    @PostMapping("/api/register")
    public Map<String, Object> register(@RequestBody ReplyDTO replyDTO, Authentication authentication){

        log.info(replyDTO);

        Map<String, Object> map = Map.of("rno", 111L);
        Object principal = authentication.getPrincipal();

        log.info(principal);

        return map;
    }

}
