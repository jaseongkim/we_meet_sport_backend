package com.sport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

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

    private String applicantName;

    private String type;

    private String category;

    private String status;

    private String message;

    private String alarmType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
