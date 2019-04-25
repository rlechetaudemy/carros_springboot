package com.carros;

import com.carros.domain.CarroService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosApplicationTests {

    @Autowired
    private CarroService service;

    @Test
    public void contextLoads() {
    }
}