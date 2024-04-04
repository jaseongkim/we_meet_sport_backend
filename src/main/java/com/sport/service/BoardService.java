package com.sport.service;

import com.sport.dto.BoardDTO;

import java.util.Map;

public interface BoardService {

    Map<String, Boolean> register(BoardDTO boardDTO);

    Map<String, Object>  modify(Long boardNo, BoardDTO boardDTO, String email);

    Map<String, Object> getBoardByNo(Long boardNo);

}
