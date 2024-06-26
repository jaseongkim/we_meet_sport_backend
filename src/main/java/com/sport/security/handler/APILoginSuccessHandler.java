package com.sport.security.handler;

import com.google.gson.Gson;
import com.sport.dto.APIUserDTO;
import com.sport.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("Login Success Handler------------------------------------------------------");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        APIUserDTO apiUserDTO = (APIUserDTO) authentication.getPrincipal();

        log.info(authentication);
        log.info(authentication.getName());
        log.info("apiUserDTO" + apiUserDTO);

        Map<String, Object> claim = Map.of("email", authentication.getName(), "nickName", apiUserDTO.getNickName(), "mobile" , apiUserDTO.getMobile());

        String accessToken = jwtUtil.generateToken(claim, 1);

        String refreshToken = jwtUtil.generateToken(claim, 30);

        Map<String, String> data = Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );

        Map<String, Object> resultMap = Map.of(
                "success", true,
                "data", data
        );

        Gson gson = new Gson();

        String jsonStr = gson.toJson(resultMap);

        response.getWriter().println(jsonStr);
    }
}
