package com.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosAPITest {
    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private CarroService service;

    @Test
    public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        // Insert
        ResponseEntity response = rest.postForEntity("/api/v1/carros",carro, null);
        System.out.println(response);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        CarroDTO c
                = rest.getForEntity(location , CarroDTO.class).getBody();

        System.out.println(c);

        assertEquals("Porshe",c.getNome());
        assertEquals("esportivos",c.getTipo());

        // Deletar o objeto
        rest.delete(location);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, rest.getForEntity(location , CarroDTO.class).getStatusCode());
    }

    @Test
    public void testLista() {
        ResponseEntity<List<CarroDTO>> response = rest.exchange(
                "/api/v1/carros/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarroDTO>>(){});
        List<CarroDTO> carros = response.getBody();
        assertEquals(30, carros.size());
    }

    @Test
    public void testListaPorTipo() {

        assertEquals(10, getCarrosByTipo("classicos").size());
        assertEquals(10, getCarrosByTipo("esportivos").size());
        assertEquals(10, getCarrosByTipo("luxo").size());

        assertNull(getCarrosByTipo("x"));
    }

    private List<CarroDTO> getCarrosByTipo(String tipo) {
        ResponseEntity<List<CarroDTO>> response = rest.exchange(
                "/api/v1/carros/tipo/"+tipo,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarroDTO>>(){});
        List<CarroDTO> carros = response.getBody();
        return carros;
    }

    @Test
    public void testGetOk() {

        ResponseEntity<CarroDTO> response
                = rest.getForEntity("/api/v1/carros/11" , CarroDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        CarroDTO c = response.getBody();
        assertEquals("Ferrari FF", c.getNome());
    }

    @Test
    public void testGetNotFound() {

        ResponseEntity<CarroDTO> response
                = rest.getForEntity("/api/v1/carros/1100" , CarroDTO.class);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}