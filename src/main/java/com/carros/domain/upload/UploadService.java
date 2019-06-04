package com.carros.domain.upload;

import com.carros.domain.upload.s3.S3Helper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Base64;

@Component
public class UploadService {

	public String upload(String fileName, String base64) throws Exception {
		byte[] bytes = Base64.getDecoder().decode(base64);
		InputStream in = new ByteArrayInputStream(bytes);

		// 1) Salva o arquivo na pasta temporaria da JVM
		File file = saveToTmpDir(fileName, in);
		// 2) Faz upload para o Cloud Storage
		String url = uploadToS3(file);
		// 3) Retorna a URL do arquivo
		return url;
	}

	private String uploadToS3(File file) throws Exception {
		// Upload
		String fileName = file.getName();
		String contentType = getContentType(fileName);
		
		S3Helper s3 = new S3Helper();
		byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
		String url = s3.putFile("cursoudemy", fileName, contentType, bytes);
		return url;
	}

	// Retorna o content-type para a extens�o fornecida
	private String getContentType(String fileName) {
		String ext = fileName.substring(fileName.indexOf("."));
		if ("png".equals(ext)) {
			return "image/png";
		} else if ("jpg".equals(ext) || "jpeg".equals(ext)) {
			return "image/jpg";
		} else if ("gif".equals(ext)) {
			return "image/gif";
		}
		return "text/plain";
	}

	private File saveToTmpDir(String fileName, InputStream in)
			throws IOException {
		if (fileName == null || in == null) {
			throw new IllegalArgumentException("Parametros invalidos");
		}
		// Pasta temporária da JVM
		File tmpDir = new File(System.getProperty("java.io.tmpdir"), "carros");
		if (!tmpDir.exists()) {
			// Cria a pasta carros se nao existe
			tmpDir.mkdir();
		}
		// Cria o arquivo
		File file = new File(tmpDir, fileName);
		System.out.println("File: " + file);
		if(file.exists()) {
			System.out.println("File delete");
			boolean ok = file.delete();
			System.out.println("File delete: " + ok);
		}
		
		// Abre a OutputStream para escrever no arquivo
		FileOutputStream out = new FileOutputStream(file);
		// Escreve os dados no arquivo
		IOUtils.copy(in, out);
		IOUtils.closeQuietly(out);
		System.out.println("File write ok: " + file);
		return file;
	}
}
