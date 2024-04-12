package com.sport.service;

import com.sport.domain.Board;
import com.sport.dto.APIUserDTO;
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
    public Map<String, Object> register(BoardDTO boardDTO, Object principal) {

        Map<String, Object> map = new HashMap<>();

        if (principal instanceof APIUserDTO) {
            // principal 객체를 APIUserDTO 타입으로 캐스팅
            APIUserDTO apiUserDTO = (APIUserDTO) principal;
            boardDTO.setNickName(apiUserDTO.getNickName());
            boardDTO.setEmail(apiUserDTO.getEmail());
        } else {
            // principal 객체가 APIUserDTO 타입이 아닌 경우의 처리
            log.info("UserDetails is not an instance of APIUserDTO");
            map.put("success", false);
            map.put("data", "UserDetails is not an instance of APIUserDTO");
            return map;
        }

        try {
            Board board = modelMapper.map(boardDTO, Board.class);
            Board responseBoard = boardRepository.save(board);
            map.put("success", true);
            map.put("data", responseBoard.getBoardNo());
        } catch (Exception e) {
            map.put("success", false);
            map.put("data",e.getMessage());
            return map;
        }

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
        String category = pageRequestDTO.getCategory();
        String type = pageRequestDTO.getType();
        Pageable pageable = pageRequestDTO.getPageable("boardNo");

        Page<Board> result = boardRepository.searchAll(searchOption, search, category, type,  pageable);

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
