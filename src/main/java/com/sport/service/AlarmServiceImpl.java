package com.sport.service;

import com.sport.domain.Alarm;
import com.sport.domain.Board;
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
    public Map<String, Object> apply(AlarmDTO alarmDTO) {

        Map<String, Object> map = new HashMap<>();

        Optional<Board> result = boardRepository.findById(alarmDTO.getBoardNo());

        Board board = result.orElseThrow();

        alarmDTO.setWriter(board.getApiUser().getEmail());
        alarmDTO.setBStatus(board.getStatus());
        alarmDTO.setTitle(board.getTitle());
        alarmDTO.setStatus("progress ");
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
