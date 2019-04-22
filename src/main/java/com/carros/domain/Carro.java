package com.carros.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String tipo;

}

