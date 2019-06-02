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

        if(username.equals("user")) {
            return User.withUsername(username).password(encoder.encode("user")).roles("USER").build();
        } else if(username.equals("admin")) {
            return User.withUsername(username).password(encoder.encode("admin")).roles("USER", "ADMIN").build();
        }

        throw new UsernameNotFoundException("user not found");
    }
}
