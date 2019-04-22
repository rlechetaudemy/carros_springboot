package com.carros.domain.dto;

import com.carros.domain.Carro;

import java.util.ArrayList;
import java.util.List;

public class CarroDTO {
    public Long id;
    public String nome;
    public String tipo;

    public CarroDTO(Carro carro) {
        this.id = carro.getId();
        this.nome = carro.getNome();
        this.tipo = carro.getTipo();
    }

    public static List<CarroDTO> toList(Iterable<Carro> list) {
        List<CarroDTO> carros = new ArrayList<>();
        for (Carro c : list) {
            carros.add(new CarroDTO(c));
        }
        return carros;
    }
}
