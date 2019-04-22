package com.carros.domain;

import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//        List<Carro> list = rep.findAll();
//        List<CarroDTO> carros = new ArrayList<>();
//        for (Carro c : list) {
//            carros.add(new CarroDTO(c));
//        }
//        return carros;
    }

    public Optional<CarroDTO> getCarroById(Long id) {
//        Optional<Carro> carro = rep.findById(id);
//        return carro.isPresent() ? Optional.of(new CarroDTO(carro.get())) : null;

        return rep.findById(id).map(CarroDTO::new);
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::new).collect(Collectors.toList());
    }

    public Carro save(Carro carro) {
        return rep.save(carro);
    }

    public void delete(Long id) {

        Optional<CarroDTO> carro = getCarroById(id);

        if(carro.isPresent()) {
            rep.deleteById(id);
        }
    }
}
