package com.carros.api;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v3/carros")
public class CarrosV3Controller {
    @Autowired
    private CarroService service;

    @GetMapping()
    public ResponseEntity get() {
        Iterable<Carro> carros = service.getCarros();
        List<CarroDTO> list = CarroDTO.toList(carros);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Carro> carro = service.getCarroById(id);

        return carro
                .map(c -> ResponseEntity.ok(new CarroDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<Carro> carros = service.getCarrosByTipo(tipo);
        List<CarroDTO> list = CarroDTO.toList(carros);

        return list.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro) {

        carro.setId(null);

        Carro c = service.save(carro);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(c.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {

        carro.setId(id);

        return service.getCarroById(id)
                .map(c -> ResponseEntity.ok(new CarroDTO(service.save(carro))))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        Optional<Carro> carro = service.getCarroById(id);

        if(carro.isPresent()) {
            service.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
