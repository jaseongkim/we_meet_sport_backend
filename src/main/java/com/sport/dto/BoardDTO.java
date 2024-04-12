package com.sport.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDTO {

    private Long boardNo;

    private String email;

    private String nickName;

    private String title;

    private String content;

    private String category;

    private String type;

    private String status;

    private Date matchDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}
