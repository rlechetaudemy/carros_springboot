package com.carros.api.security.jwt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
            // Não informou o authorization
            filterChain.doFilter(request, response);
            return;
        }

        if(! JwtHelper.isTokenValid(token)) {
            throw new AuthorizationException("Acesso negado.");
        }

        try {

            String login = JwtHelper.getLogin(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(login);

            List<GrantedAuthority> authorities = JwtHelper.getRoles(token);

            //var authorities = ((UserDetails) userDetails).getAuthorities();

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

            // Salva o Authentication no contexto do Spring
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        } catch (AuthenticationException ex) {
            throw new ServletException("Authentication error.");
        }
    }
}
