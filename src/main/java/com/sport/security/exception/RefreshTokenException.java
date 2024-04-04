package com.sport.security.exception;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RefreshTokenException extends RuntimeException {

    private ErrorCase errorCase;

    public enum ErrorCase {
        NO_ACCESS, NO_REFRESH, OLD_REFRESH, BAD_REFRESH
    }

    public RefreshTokenException(ErrorCase errorCase){
        super(errorCase.name());
        this.errorCase = errorCase;
    }

    public void sendResponseError(HttpServletResponse response){

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        String responseStr = gson.toJson(Map.of("success", false, "data", errorCase.name()));

        try {
            response.getWriter().println(responseStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
