package com.carros;

import com.carros.api.upload.UploadInput;
import com.carros.api.upload.UploadOutput;
import com.carros.domain.upload.FirebaseStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadTest extends BaseAPITest {
    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private FirebaseStorageService service;

    private UploadInput getUploadInput() {
        UploadInput upload = new UploadInput();
        upload.setFileName("nome.txt");
        // Base64 de Ricardo Lecheta
        upload.setBase64("UmljYXJkbyBMZWNoZXRh");
        upload.setMimeType("text/plain");
        return upload;
    }

    @Test
    public void testUploadFirebase() {
        String url = service.upload(getUploadInput());

        // Faz o Get na URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(urlResponse);
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

    @Test
    public void testUploadAPI() {

        UploadInput upload = getUploadInput();

        // Insert
        ResponseEntity<UploadOutput> response = post("/api/v1/upload", upload, UploadOutput.class);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UploadOutput out = response.getBody();
        assertNotNull(out);

        String url = out.getUrl();
        System.out.println(url);

        // Faz o Get na URL
        ResponseEntity<String> urlResponse = rest.getForEntity(url, String.class);
        System.out.println(urlResponse);
        assertEquals(HttpStatus.OK, urlResponse.getStatusCode());
    }

}