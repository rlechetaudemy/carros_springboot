package com.carros.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarroRepository extends CrudRepository<Carro, Long> {

    List<Carro> findByTipo(String tipo);
}
