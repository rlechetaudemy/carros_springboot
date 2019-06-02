package com.carros.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // USER, ADMIN, etc
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;

    @Override
    public String getAuthority() {
        return nome;
    }
}
