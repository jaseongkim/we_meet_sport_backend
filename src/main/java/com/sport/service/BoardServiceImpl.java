package com.sport.service;

import com.sport.domain.Board;
import com.sport.dto.BoardDTO;
import com.sport.dto.PageRequestDTO;
import com.sport.dto.PageResponseDTO;
import com.sport.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Map<String, Object> resultMap = Map.of(
                "success", true,
                "data", boardChangeDTO
        );

        return resultMap;
    }

    @Override
    public Map<String, Object> getBoardByNo(Long boardNo) {

        Optional<Board> result = boardRepository.findById(boardNo);

        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);

        Map<String, Object> resultMap = Map.of(
                "success", true,
                "data", boardDTO
        );

        return resultMap;
    }

    @Override
    public Map<String, Object> remove(Long boardNo, String email) {

        Map<String, Object> map = new HashMap<>();

        Optional<Board> result = boardRepository.findById(boardNo);

        Board board = result.orElseThrow();

        if(!board.getApiUser().getEmail().equals(email)){
            map.put("success", false);
            map.put("data", "permissionDenied");
            return map;
        }

        try{
            boardRepository.deleteById(board.getBoardNo());
        }catch (Exception e) {
            map.put("success", false);
            map.put("data", e.getMessage());
        }

        map.put("success", true);

        return map;
    }

    @Override
    public Map<String, Object> searchAll(PageRequestDTO pageRequestDTO) {

        String searchOption = pageRequestDTO.getSearchOption();
        String search = pageRequestDTO.getSearch();
        Pageable pageable = pageRequestDTO.getPageable("boardNo");

        Page<Board> result = boardRepository.searchAll(searchOption, search, pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());

        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

        Map<String, Object> map = new HashMap<>();

        map.put("success", true);
        map.put("data", pageResponseDTO);

        return map;
    }


}
