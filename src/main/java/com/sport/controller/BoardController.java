package com.sport.controller;

import com.sport.dto.APIUserDTO;
import com.sport.dto.BoardDTO;
import com.sport.dto.PageRequestDTO;
import com.sport.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 등록
    @PostMapping("/api/register")
    public Map<String, Boolean> register(@RequestBody BoardDTO boardDTO, Authentication authentication){

        Map<String, Boolean> map = null;
        Object principal = authentication.getPrincipal();

        if (principal instanceof APIUserDTO) {
            // principal 객체를 APIUserDTO 타입으로 캐스팅
            APIUserDTO apiUserDTO = (APIUserDTO) principal;
            boardDTO.setNickName(apiUserDTO.getNickName());
            boardDTO.setEmail(apiUserDTO.getEmail());

            map = boardService.register(boardDTO);
        } else {
            // principal 객체가 APIUserDTO 타입이 아닌 경우의 처리
            log.info("UserDetails is not an instance of APIUserDTO");
            return Collections.singletonMap("success", false);
        }
        return map;
    }

    // 게시글 수정
    @PutMapping ("/api/{boardNo}")
    public Map<String, Object> modify(@PathVariable("boardNo") Long boardNo, @RequestBody BoardDTO boardDTO, Principal principal){
        Map<String, Object> map = null;

        String email = principal.getName();

        map = boardService.modify(boardNo, boardDTO, email);

        return map;
    }

    // 게시글 조회
    @GetMapping("/{boardNo}")
    public Map<String, Object> getBoardByNo(@PathVariable("boardNo") Long boardNo){

        Map<String, Object> map = null;

        map = boardService.getBoardByNo(boardNo);

        return map;
    }

    // 게시글 삭제
    @DeleteMapping("/api/{boardNo}")
    public Map<String, Object> remove(@PathVariable("boardNo") Long boardNo, Principal principal) {

        Map<String, Object> map = null;

        String email = principal.getName();

        map = boardService.remove(boardNo, email);

        return map;
    }

    // 게시글 전체 조회
    @GetMapping("/list")
    public Map<String, Object> list(@RequestBody PageRequestDTO pageRequestDTO){

        Map<String, Object> map = null;

        map = boardService.searchAll(pageRequestDTO);

        return map;
    }


}
