package com.sport.service;

import com.sport.dto.APIUserJoinDTO;

import java.util.Map;

public interface APIUserService {

    Map<String,Object> signup(APIUserJoinDTO apiUserJoinDTO);


}
