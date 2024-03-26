package com.sport.service;

import com.sport.domain.APIUser;
import com.sport.domain.APIUserRole;
import com.sport.dto.APIUserJoinDTO;
import com.sport.repository.APIUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class APIUserServiceImpl implements APIUserService {

    private final ModelMapper modelMapper;

    private final APIUserRepository apiUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(APIUserJoinDTO apiUserJoinDTO)  {

        APIUser apiUser = modelMapper.map(apiUserJoinDTO, APIUser.class);
        apiUser.changeMpw(passwordEncoder.encode(apiUserJoinDTO.getMpw()));
        apiUser.addRole(APIUserRole.USER);

        log.info("-----------------------------------------");
        log.info(apiUser);
        log.info(apiUser.getRoleSet());

        apiUserRepository.save(apiUser);
    }
}
