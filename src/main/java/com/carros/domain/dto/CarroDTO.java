package com.carros.domain.dto;

import com.carros.domain.Carro;
import lombok.Data;

@Data
public class CarroDTO {
    public Long id;
    public String nome;
    public String tipo;

    public CarroDTO(Carro carro) {
        // Aula 36: fizemos a cópia manualmente
        this.id = carro.getId();
        this.nome = carro.getNome();
        this.tipo = carro.getTipo();

        // Aula 38: fizemos a cópia com Model Mapper

    }
}
