package com.carros.api.security.jwt;

import com.carros.api.exception.MsgError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String AUTH_URL = "/api/v1/login";

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        // api/authenticate
        setFilterProcessesUrl(AUTH_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {

        try {
            JwtLoginInput login = new ObjectMapper().readValue(request.getInputStream(), JwtLoginInput.class);
            String username = login.getUsername();
            String password = login.getPassword();

            Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(auth);
        } catch (IOException e) {
            throw new BadCredentialsException("Authentication error");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        String jwtToken = JwtUtil.createToken(user);

        String json = ServletUtil.getJson("token", jwtToken);
        ServletUtil.write(response, HttpStatus.OK, json);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException failed) throws IOException, ServletException {

        String json = ServletUtil.getJson("error", "Login incorreto");
        ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
    }


}


