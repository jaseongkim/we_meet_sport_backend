package com.sport.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDTO {

    private Long alarmNo;

    private Long boardNo;

    private Boolean bStatus;

    private String title;

    private String writer;

    private String applicant;

    private String type;

    private String category;

    private String status;

    private String message;
}
