package com.carros.api.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if("teste".equals(username)) {
            return User.withUsername(username).password(encoder.encode(username)).roles("USER").build();
        } else if("admin".equals(username)) {
            return User.withUsername(username).password(encoder.encode(username)).roles("USER", "ADMIN").build();
        }
        return null;
    }
}
