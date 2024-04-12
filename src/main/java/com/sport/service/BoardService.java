package com.sport.service;

import com.sport.dto.BoardDTO;
import com.sport.dto.PageRequestDTO;

import java.util.Map;

public interface BoardService {

    Map<String, Object> register(BoardDTO boardDTO, Object principal);

    Map<String, Object>  modify(Long boardNo, BoardDTO boardDTO, String email);

    Map<String, Object> getBoardByNo(Long boardNo);

    Map<String, Object> remove(Long boardNo, String email);

    Map<String, Object> searchAll(PageRequestDTO pageRequestDTO);

}
