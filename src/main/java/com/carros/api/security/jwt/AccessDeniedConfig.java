package com.carros.api.security.jwt;

import com.carros.api.exception.MsgError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedConfig implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exc) throws IOException {

        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            String json = new ObjectMapper().writeValueAsString(new MsgError("Acesso negado"));
            response.getWriter().write(json);
        }
    }
}
