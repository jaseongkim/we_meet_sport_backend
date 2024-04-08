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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Service
@RequiredArgsConstructor
public class APIUserServiceImpl implements APIUserService {

    private final ModelMapper modelMapper;

    private final APIUserRepository apiUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Map<String,Object> signup(APIUserJoinDTO apiUserJoinDTO)  {
        Map<String,Object> map = new HashMap<>();

        if(apiUserJoinDTO.getPassword().length() >= 10) {
            map = Map.of("success", false, "data", "비밀번호 오류");
            return map;
        } else if (apiUserJoinDTO.getNickName().length() >=10) {
            map = Map.of("success", false, "data", "닉네임 오류");
            return map;
        } else if (apiUserRepository.existsBynickName(apiUserJoinDTO.getNickName())) {
            map = Map.of("success", false, "data", "닉네임 중복");
            return map;
        } else if (apiUserRepository.existsByemail(apiUserJoinDTO.getEmail())) {
            map = Map.of("success", false, "data", "이메일 중복");
            return map;
        } else if (!emailValidator(apiUserJoinDTO.getEmail())) {
            map = Map.of("success", false, "data", "이메일 오류");
            return map;
        }

        APIUser apiUser = modelMapper.map(apiUserJoinDTO, APIUser.class);
        apiUser.changeMpw(passwordEncoder.encode(apiUserJoinDTO.getPassword()));
        apiUser.addRole(APIUserRole.USER);

        log.info("-----------------------------------------");
        log.info(apiUser);
        log.info(apiUser.getRoleSet());

        apiUserRepository.save(apiUser);

        map = Map.of("success", true);

        return map;
    }

    public boolean emailValidator(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
