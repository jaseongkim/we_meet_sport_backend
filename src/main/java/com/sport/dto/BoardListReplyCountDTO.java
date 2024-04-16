package com.sport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {

    private Long boardNo;

    private String title;

    private String nickName;

    private String category;

    private String type;

    private Boolean status;

    private LocalDate matchDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private Long replyCount;
}
