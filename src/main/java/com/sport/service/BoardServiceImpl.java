package com.sport.service;

import com.sport.domain.Board;
import com.sport.dto.BoardDTO;
import com.sport.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;

    @Override
    public Map<String, Boolean> register(BoardDTO boardDTO) {

        Board board = modelMapper.map(boardDTO, Board.class);

        Map<String, Boolean> map = new HashMap<>();

        try {
            boardRepository.save(board);
        } catch (Exception e) {
            map.put("success", false);
            log.info(e.getMessage());
            return map;
        }
        map.put("success", true);

        return map;

    }

    @Override
    public Map<String, Object> modify(Long boardNo, BoardDTO boardDTO, String email) {

        Map<String, Object> map = new HashMap<>();

        Optional<Board> result = boardRepository.findById(boardNo);

        Board board = result.orElseThrow();

        if(!email.equals(board.getApiUser().getEmail())) {
            map.put("success", false);
            map.put("data", "permissionDenied");
            return map;
        }

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        boardRepository.save(board);

        BoardDTO boardChangeDTO = modelMapper.map(board, BoardDTO.class);

        map.put("success", true);
        map.put("BoardDTO", boardChangeDTO);

        return map;
    }

    @Override
    public BoardDTO getBoardByNo(Long boardNo) {

        Optional<Board> result = boardRepository.findById(boardNo);

        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }
}
