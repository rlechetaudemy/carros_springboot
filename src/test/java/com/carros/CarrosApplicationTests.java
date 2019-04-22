package com.carros;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosApplicationTests {

    @Autowired
    private CarroService service;

    @Test
    public void contextLoads() {

//        Carro c = new Carro();
//        c.setNome("Novo carro");
//        c.setTipo("esportivos");
//
//        service.save(c);
//
//        Long id = c.getId();
//
//        assertNotNull(id);
    }

}
