package com.carros.api.upload;

import lombok.Data;

@Data
public class UploadInput {
    private String fileName;
    private String base64;
    private String mimeType;
}
