package com.sport.service;

import com.sport.domain.Alarm;
import com.sport.domain.Board;
import com.sport.dto.AlarmDTO;

import java.util.Map;

public interface AlarmService {

    Map<String, Object> apply (AlarmDTO alarmDTO, Object principal);

    Map<String, Object> list(String email);

    default Alarm dtoToEntity(AlarmDTO alarmDTO, Board board) {
        Alarm alarm = Alarm.builder()
                .board(board)
                .bStatus(board.getStatus())
                .title(alarmDTO.getTitle())
                .writer(alarmDTO.getWriter())
                .applicant(alarmDTO.getApplicant())
                .applicantName(alarmDTO.getApplicantName())
                .type(alarmDTO.getType())
                .category(alarmDTO.getCategory())
                .status(alarmDTO.getStatus())
                .message(alarmDTO.getMessage())
                .AlarmType("apply")
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
                .applicantName(alarm.getApplicantName())
                .type(alarm.getType())
                .category(alarm.getCategory())
                .status(alarm.getStatus())
                .message(alarm.getMessage())
                .alarmType(alarm.getAlarmType())
                .createdAt(alarm.getCreatedAt())
                .build();

        return alarmDTO;
    }


}
