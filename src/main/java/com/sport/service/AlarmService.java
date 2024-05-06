package com.sport.service;

import com.sport.domain.Alarm;
import com.sport.domain.Board;
import com.sport.dto.AlarmDTO;

import java.util.Map;

public interface AlarmService {

    Map<String, Object> apply (AlarmDTO alarmDTO);

    Map<String, Object> list(String email);

    default Alarm dtoToEntity(AlarmDTO alarmDTO, Board board) {
        Alarm alarm = Alarm.builder()
                .board(board)
                .bStatus(alarmDTO.getBStatus())
                .title(alarmDTO.getTitle())
                .writer(alarmDTO.getWriter())
                .applicant(alarmDTO.getApplicant())
                .type(alarmDTO.getType())
                .category(alarmDTO.getCategory())
                .status(alarmDTO.getStatus())
                .message(alarmDTO.getMessage())
                .build();
        return alarm;
    }

    default AlarmDTO entityToDto(Alarm alarm) {
        AlarmDTO alarmDTO = AlarmDTO.builder()
                .alarmNo(alarm.getAlarmNo())
                .boardNo(alarm.getBoard().getBoardNo())
                .bStatus(alarm.getBStatus())
                .title(alarm.getTitle())
                .writer(alarm.getWriter())
                .applicant(alarm.getApplicant())
                .type(alarm.getType())
                .category(alarm.getCategory())
                .status(alarm.getStatus())
                .message(alarm.getMessage())
                .build();

        return alarmDTO;
    }


}
