package com.carros;

import com.carros.api.security.jwt.JwtHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class)
public class TokenJwtTest {
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Test
    public void testToken() {

        // Le usuário
        UserDetails user = userDetailsService.loadUserByUsername("admin");
        assertNotNull(user);

        // Gera token
        String jwtToken = JwtHelper.createToken(user);
        System.out.println(jwtToken);
        assertNotNull(jwtToken);

        // Valida Token
        boolean ok = JwtHelper.isTokenValid(jwtToken);
        assertTrue(ok);

        // Valida login
        String login = JwtHelper.getLogin(jwtToken);
        assertEquals("admin",login);

        // Valida roles
        List<GrantedAuthority> roles = JwtHelper.getRoles(jwtToken);
        assertNotNull(roles);
        System.out.println(roles);
        String role = roles.get(0).getAuthority();
        assertEquals(role,"ROLE_ADMIN");
    }

}