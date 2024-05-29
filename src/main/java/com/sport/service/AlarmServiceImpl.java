package com.sport.service;

import com.sport.domain.Alarm;
import com.sport.domain.Board;
import com.sport.dto.APIUserDTO;
import com.sport.dto.AlarmDTO;
import com.sport.repository.AlarmRepository;
import com.sport.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;

    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;

    @Override
    public Map<String, Object> apply(AlarmDTO alarmDTO, Object principal) {

        Map<String, Object> map = new HashMap<>();

        if (principal instanceof APIUserDTO) {
            // principal 객체를 APIUserDTO 타입으로 캐스팅
            APIUserDTO apiUserDTO = (APIUserDTO) principal;
            alarmDTO.setApplicant(apiUserDTO.getEmail());
            alarmDTO.setApplicantName(apiUserDTO.getNickName());
        } else {
            // principal 객체가 APIUserDTO 타입이 아닌 경우의 처리
            log.info("UserDetails is not an instance of APIUserDTO");
            map.put("success", false);
            map.put("data", "UserDetails is not an instance of APIUserDTO");
            return map;
        }

        Optional<Board> result = boardRepository.findById(alarmDTO.getBoardNo());

        Board board = result.orElseThrow();

        Optional<Alarm> alarmResult = alarmRepository.findAlarmByBoardAndApplicant(board, alarmDTO.getApplicant());

        try{
            Alarm alarmExists  = alarmResult.orElseThrow();
            if(alarmExists != null) {
                map.put("success", false);
                map.put("data", "이미 신청한 내용이 있습니다.");
                return map;
            }
        } catch (Exception e) {

        }

        alarmDTO.setWriter(board.getApiUser().getEmail());
        alarmDTO.setTitle(board.getTitle());
        alarmDTO.setStatus("progress");
        alarmDTO.setCategory(board.getCategory());
        alarmDTO.setType(board.getType());

        try {
            Alarm alarm = dtoToEntity(alarmDTO, board);
            alarmRepository.save(alarm);

        } catch (Exception e) {
            map.put("success", false);
            map.put("data", e.getMessage());
            return map;
        }
        map.put("success", true);

        return map;
    }

    @Override
    public Map<String, Object> list(String email) {
        Map<String, Object> map = new HashMap<>();

        try {
            List<Alarm> list = alarmRepository.findAllByWriter(email);
            List<AlarmDTO> dtoList = list.stream().map(alarm->entityToDto(alarm)).collect(Collectors.toList());
            map.put("success", true);
            map.put("data", dtoList);
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("data", e.getMessage());
            return map;
        }
    }
}
