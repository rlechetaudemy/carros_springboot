package com.carros.api.upload;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import com.carros.domain.upload.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {

    @Autowired
    private FirebaseStorageService uploadService;

//    @PostMapping("/upload")
//    public ResponseEntity upload(@RequestParam String fileName, @RequestParam String base64) {
//
//        String s = "Filename: " + fileName + " >> base64 > " + base64;
//
//        return ResponseEntity.ok(s);
//    }

    @PostMapping
    public ResponseEntity upload(@RequestBody UploadInput uploadInput) throws IOException {

//        String url = "Filename: " + uploadInput.getFileName() + " >> base64 > " + uploadInput.getBase64();

        String url = uploadService.upload(uploadInput);

        return ResponseEntity.ok(new UploadOutput(url));
    }
}
