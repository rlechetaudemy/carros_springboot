package com.carros.domain.upload.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class S3Helper {

	public static final String S3_SERVER = "https://s3-sa-east-1.amazonaws.com";
	public static final String BUCKET = "udemy-temp";

	public AmazonS3 connect() {
		AmazonS3 conn = new AmazonS3Client(new S3Credencials() );
		return conn;
	}

	public String putFile(String dir, String fileName, String contentType, byte[] bytes) throws IOException {
		AmazonS3 conn = connect();

		String path = StringUtils.isNotEmpty(dir) ? dir+"/"+fileName : fileName;

		ObjectMetadata metadata = new ObjectMetadata();
		if(contentType != null) {
			metadata.setContentType(contentType);
			metadata.setContentLength(bytes != null ? bytes.length : 0);
		}

		PutObjectResult result;

		if(bytes != null) {
			result = conn.putObject(BUCKET, path, new ByteArrayInputStream(bytes), metadata);
		} else {
			result = conn.putObject(BUCKET, path, new ByteArrayInputStream(new byte[]{}),metadata);
		}
		
		// Publico
		conn.setObjectAcl(BUCKET, path, CannedAccessControlList.PublicRead);

		String url = conn.getUrl(BUCKET, path).toString();

		return url;
	}

	public static void main(String[] args) throws IOException {
		S3Helper s = new S3Helper();
		String url = s.putFile("livrows", "nome.txt", "text/plan", "Ricardo Ninja Lecheta".getBytes());
		System.out.println(url);
	}
}
