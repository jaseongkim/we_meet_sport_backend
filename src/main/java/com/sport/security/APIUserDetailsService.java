package com.sport.security;

import com.sport.domain.APIUser;
import com.sport.dto.APIUserDTO;
import com.sport.repository.APIUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {

    private final APIUserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<APIUser> result = apiUserRepository.getWithRoles(username);

        APIUser apiUser = result.orElseThrow(()->new UsernameNotFoundException("Cannot find"));

        log.info("APIUserDetailService apiUser------------------------------------------------");

        APIUserDTO apiUserDTO = new APIUserDTO(
                apiUser.getEmail(),
                apiUser.getPassword(),
                apiUser.getNickName(),
                apiUser.getMobile(),
                apiUser.getRoleSet().stream().map(apiUserRole -> new SimpleGrantedAuthority("ROLE_"+apiUserRole.name()))
                                .collect(Collectors.toList())
        );

        log.info(apiUserDTO);

        return apiUserDTO;
    }
}
