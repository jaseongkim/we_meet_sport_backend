package com.sport.security.filter;


import com.google.gson.Gson;
import com.sport.security.exception.RefreshTokenException;
import com.sport.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshPath;

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if(!path.equals(refreshPath)) {
            log.info("skip refresh token filter......");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Refresh Token Filter...run .........................1");

        // 전송된 JSON에서 accessToken과 refreshToken을 얻어온다
        Map<String, String> tokens = parseRequestJSON(request);

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        try{
            checkAccessToken(accessToken);
        }catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }

        Map<String, Object> refreshClaim = null;

        try{
            refreshClaim = checkRefreshToken(refreshToken);
            log.info(refreshClaim);

            // 유효기간이 얼마 남지 않은 경우
            Integer exp = (Integer)refreshClaim.get("exp");

            Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli()*1000);

            Date current = new Date(System.currentTimeMillis());

            // 만료 시간과 현재 시간의 간격의 계산
            // 만일 3일 미만의 경우에는 Refresh Token도 다시 생성
            long gapTime = (expTime.getTime() - current.getTime());

            log.info("-----------------------------------------------");

            log.info("current : " + current);
            log.info("expTime: " + expTime);
            log.info("gap: " + gapTime);

            String email = (String)refreshClaim.get("email");
            String nickName = (String)refreshClaim.get("nickName");
            String mobile = (String)refreshClaim.get("mobile");

            Map<String, Object> claim = Map.of("email",email, "nickName", nickName, "mobile" , mobile);

            // 이 상태까지 오면 무조건 AccessToken은 새로 생성
            String accessTokenValue = jwtUtil.generateToken(claim, 1);
            String refreshTokenValue = tokens.get("refreshToken");

            //RefreshToekn이 3일도 안남았다면
            if(gapTime < (1000 * 60 * 60 * 24 * 3)) {
                log.info("new Refresh Token required...");
                refreshTokenValue = jwtUtil.generateToken(claim,30);
            }

            log.info("Refresh Token result..................");
            log.info("new Access Token : " + accessTokenValue);
            log.info("new Refresh Token : " + refreshTokenValue);

            sendTokens(accessTokenValue, refreshTokenValue, response);

        }catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }

        log.info("old accessToken: " + accessToken);
        log.info("old refreshToken: " + refreshToken);
    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {

        try(Reader reader = new InputStreamReader(request.getInputStream())){

            Gson gson = new Gson();

            return gson.fromJson(reader, Map.class);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private void checkAccessToken(String accessToken) throws RefreshTokenException {
        try{
            jwtUtil.validateToken(accessToken);
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("Access Token has expired");
        } catch (Exception exception) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    private Map<String, Object> checkRefreshToken(String refreshToken) throws RefreshTokenException {
        try{
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);
            return values;
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        } catch (MalformedJwtException malformedJwtException) {
            log.error("MalformedJwtException--------------------------");
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        } catch (Exception exception) {
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }

    private void sendTokens(String accessTokenValue, String refreshTokenValue,HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        Map<String, String> data = Map.of(
                "accessToken", accessTokenValue,
                "refreshToken", refreshTokenValue
        );

        Map<String, Object> resultMap = Map.of(
                "success", true,
                "data", data
        );

        String jsonStr = gson.toJson(resultMap);

        try {
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
