package com.sport.service;

import com.sport.domain.Board;
import com.sport.dto.*;
import com.sport.repository.BoardRepository;
import com.sport.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

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
            if(boardDTO.getStatus() == null){
                boardDTO.setStatus(false);
            }
            Board board = modelMapper.map(boardDTO, Board.class);

            Board responseBoard = boardRepository.save(board);
            map.put("success", true);
            map.put("data", responseBoard.getBoardNo());
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("data",e.getMessage());
            return map;
        }
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

        log.info(boardDTO);

        if(boardDTO.getMatchDate() == null) {
            boardDTO.setMatchDate(board.getMatchDate());
        }
        if(boardDTO.getStatus() == null){
            boardDTO.setStatus(board.getStatus());
        }

        board.change(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getStatus(), boardDTO.getMatchDate());

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
    @Transactional
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
            replyRepository.deleteAllByBoard_BoardNo(boardNo);

            boardRepository.deleteById(boardNo);
            map.put("success", true);
        }catch (Exception e) {
            map.put("success", false);
            map.put("data", e.getMessage());
        }

        return map;
    }

//    @Override
//    public Map<String, Object> searchAll(PageRequestDTO pageRequestDTO) {
//
//        String searchOption = pageRequestDTO.getSearchOption();
//        String search = pageRequestDTO.getSearch();
//        String category = pageRequestDTO.getCategory();
//        String type = pageRequestDTO.getType();
//        Pageable pageable = pageRequestDTO.getPageable("boardNo");
//
//        Page<Board> result = boardRepository.searchAll(searchOption, search, category, type,  pageable);
//
//        List<BoardDTO> dtoList = result.getContent().stream()
//                .map(board -> modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());
//
//        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int)result.getTotalElements())
//                .build();
//
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("success", true);
//        map.put("data", pageResponseDTO);
//
//        return map;
//    }

    @Override
    public Map<String, Object> searchWithReplyCount(PageRequestDTO pageRequestDTO) {

        String searchOption = pageRequestDTO.getSearchOption();
        String search = pageRequestDTO.getSearch();
        String category = pageRequestDTO.getCategory();
        String type = pageRequestDTO.getType();
        Boolean status = pageRequestDTO.getStatus();
        LocalDate from = pageRequestDTO.getFrom();
        LocalDate to = pageRequestDTO.getTo();

        Pageable pageable = pageRequestDTO.getPageable("boardNo");

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(searchOption, search, category, type,
                status, from, to, pageable);

        PageResponseDTO<BoardListReplyCountDTO> pageResponseDTO = PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();

        Map<String, Object> map = new HashMap<>();

        map.put("success", true);
        map.put("data", pageResponseDTO);

        return map;
    }

}