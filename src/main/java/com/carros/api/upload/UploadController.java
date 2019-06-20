package com.carros.api.upload;

import com.carros.domain.upload.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

        String url = uploadService.upload(uploadInput);

        return ResponseEntity.ok(new UploadOutput(url));
    }
}
