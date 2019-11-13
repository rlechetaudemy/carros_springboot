package com.carros.domain.dto;

import com.carros.domain.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarroDTO {
    public Long id;
    public String nome;
    public String tipo;

    // Aula 36: fizemos a cópia manualmente
    /*public CarroDTO(Carro c) {

        this.id = c.getId();
        this.nome = c.getNome();
        this.tipo = c.getTipo();
    }*/

    // Aula 38: fizemos a cópia com Model Mapper
    public static CarroDTO create(Carro c) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(c, CarroDTO.class);
    }
}
