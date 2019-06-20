package com.carros.api.carros;

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
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService service;

    @GetMapping()
    public ResponseEntity get() {
        List<CarroDTO> carros = service.getCarros();
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        CarroDTO carro = service.getCarroById(id);

        return ResponseEntity.ok(carro);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carros = service.getCarrosByTipo(tipo);
        return carros.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carros);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody Carro carro) {

        CarroDTO c = service.insert(carro);

        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {

        carro.setId(id);

        CarroDTO c = service.update(carro, id);

        return c != null ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
