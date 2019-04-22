package com.carros.domain;

import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {
        List<CarroDTO> list = rep.findAll().stream().map(CarroDTO::new).collect(Collectors.toList());
        return list;
    }

    public Optional<CarroDTO> getCarroById(Long id) {
        Optional<Carro> carro = rep.findById(id);
        return carro.map(CarroDTO::new);
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::new).collect(Collectors.toList());
    }

    public Optional<CarroDTO> save(Carro carro) {
        if (carro.getId() != null) {
//            throw new RuntimeException("Erro ao inserir");
            return null;
        }
        Carro c = rep.save(carro);
        return Optional.ofNullable(c).map(CarroDTO::new);
    }

    public CarroDTO update(Carro carro, Long id) {
        carro.setId(id);

        return getCarroById(id).map(c -> save(carro).get()).orElse(null);
    }

    public boolean delete(Long id) {

        Optional<CarroDTO> carro = getCarroById(id);

        return carro.map(c -> {
            rep.deleteById(id);
            return true;
        }).orElse(false);
    }
}
