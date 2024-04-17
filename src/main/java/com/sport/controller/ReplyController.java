package com.sport.controller;

import com.sport.dto.ReplyDTO;
import com.sport.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/api/register")
    public Map<String, Object> register(@RequestBody ReplyDTO replyDTO, Authentication authentication){

        Map<String, Object> map = null;
        Object principal = authentication.getPrincipal();

        map = replyService.register(replyDTO,principal);

        return map;
    }

    @PutMapping ("/api/{replyNo}")
    public Map<String, Object> register(@PathVariable("replyNo") Long replyNo,@RequestBody ReplyDTO replyDTO, Authentication authentication){

        Map<String, Object> map = null;
        Object principal = authentication.getPrincipal();

        map = replyService.modify(replyNo,replyDTO,principal);

        return map;
    }

    @DeleteMapping("/api/{replyNo}")
    public Map<String, Object> remove(@PathVariable("replyNo") Long replyNo, Authentication authentication){

        Map<String, Object> map = null;

        Object principal = authentication.getPrincipal();

        map = replyService.remove(replyNo,principal);

        return map;
    }
}
