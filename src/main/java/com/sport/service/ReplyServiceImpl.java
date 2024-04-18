package com.sport.service;

import com.sport.domain.Reply;
import com.sport.dto.APIUserDTO;
import com.sport.dto.PageRequestDTO;
import com.sport.dto.PageResponseDTO;
import com.sport.dto.ReplyDTO;
import com.sport.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;

    @Override
    public Map<String, Object> register(ReplyDTO replyDTO, Object principal) {

        Map<String, Object> map = new HashMap<>();

        if (principal instanceof APIUserDTO) {
            // principal 객체를 APIUserDTO 타입으로 캐스팅
            APIUserDTO apiUserDTO = (APIUserDTO) principal;
            replyDTO.setNickName(apiUserDTO.getNickName());
            replyDTO.setEmail(apiUserDTO.getEmail());
        } else {
            // principal 객체가 APIUserDTO 타입이 아닌 경우의 처리
            log.info("UserDetails is not an instance of APIUserDTO");
            map.put("success", false);
            map.put("data", "UserDetails is not an instance of APIUserDTO");
            return map;
        }
        try {
            Reply reply = modelMapper.map(replyDTO, Reply.class);
            Reply responseReply = replyRepository.save(reply);

            map.put("success", true);
            map.put("data", responseReply.getReplyNo());
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("data",e.getMessage());
            return map;
        }
    }

    @Override
    public Map<String, Object> modify(Long replyNo, ReplyDTO replyDTO, Object principal) {
        Map<String, Object> map = new HashMap<>();

        Optional<Reply> result = replyRepository.findById(replyNo);

        Reply reply = result.orElseThrow();

        if (principal instanceof APIUserDTO) {
            // principal 객체를 APIUserDTO 타입으로 캐스팅
            APIUserDTO apiUserDTO = (APIUserDTO) principal;

            if(!apiUserDTO.getEmail().equals(reply.getEmail())) {
                map.put("success", false);
                map.put("data", "permissionDenied");
                return map;
            }
            reply.change(replyDTO.getReplyText(), apiUserDTO.getNickName());

            replyRepository.save(reply);

            ReplyDTO responseReplyDTO = modelMapper.map(reply, ReplyDTO.class);

            map.put("success", true);
            map.put("data", responseReplyDTO);

            return map;

        } else {
            // principal 객체가 APIUserDTO 타입이 아닌 경우의 처리
            log.info("UserDetails is not an instance of APIUserDTO");
            map.put("success", false);
            map.put("data", "UserDetails is not an instance of APIUserDTO");
            return map;
        }
    }

    @Override
    public Map<String, Object> remove(Long replyNo, Object principal) {

        Map<String, Object> map = new HashMap<>();

        Optional<Reply> result = replyRepository.findById(replyNo);

        Reply reply = result.orElseThrow();

        if (principal instanceof APIUserDTO) {
            // principal 객체를 APIUserDTO 타입으로 캐스팅
            APIUserDTO apiUserDTO = (APIUserDTO) principal;

            if(!apiUserDTO.getEmail().equals(reply.getEmail())) {
                map.put("success", false);
                map.put("data", "permissionDenied");
                return map;
            }

            try{
                replyRepository.deleteById(replyNo);
                map.put("success" , true);
            }catch (Exception e) {
                map.put("success", false);
                map.put("data", e.getMessage());
            }

            return map;

        } else {
            // principal 객체가 APIUserDTO 타입이 아닌 경우의 처리
            log.info("UserDetails is not an instance of APIUserDTO");
            map.put("success", false);
            map.put("data", "UserDetails is not an instance of APIUserDTO");
            return map;
        }
    }

    @Override
    public Map<String, Object> list(Long boardNo, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPageNo() <=0? 0:pageRequestDTO.getPageNo()-1,pageRequestDTO.getPageSize(), Sort.by("replyNo").descending());

        Page<Reply> result = replyRepository.listOfBoard(boardNo, pageable);

        List<ReplyDTO> dtoList = result.getContent().stream().map(reply->modelMapper.map(reply, ReplyDTO.class)).collect(Collectors.toList());

        PageResponseDTO<ReplyDTO> pageResponseDTO = PageResponseDTO.<ReplyDTO>withAll()
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
