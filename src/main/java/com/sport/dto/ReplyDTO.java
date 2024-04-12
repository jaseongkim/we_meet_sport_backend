package com.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long replyNo;

    private Long boardNo;

    private String replyText;

    private String replyer;

    private LocalDateTime regDate, modeDate;

}
