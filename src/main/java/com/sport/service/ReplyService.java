package com.sport.service;

import com.sport.dto.ReplyDTO;

import java.util.Map;

public interface ReplyService {

    Map<String, Object> register(ReplyDTO replyDTO, Object principal);
    Map<String, Object> modify(Long replyNo, ReplyDTO replyDTO, Object principal);
    Map<String, Object> remove(Long replyNo, Object principal);


}
