package com.carros;

import com.carros.domain.upload.FirebaseStorageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Esta classe fiz apenas para demonstrar a utilização básica.
 * Depois evoluimos para uma classe de "Service" com teste unitário
 * Deixei aqui só para ficar como histórico.
 * Mas em um projeto real esse arquivo seria excluído.
 */
public class HelloFirebaseStorage {
    public static void main(String[] args) throws IOException {
        InputStream in =
                FirebaseStorageService.class.getResourceAsStream("/serviceAccountKey.json");

        System.out.println(in);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(in))
                .setStorageBucket("carros-3a13e.appspot.com")
                .setDatabaseUrl("https://carros-3a13e.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        Bucket bucket = StorageClient.getInstance().bucket();
        System.out.println(bucket);

        // Upload
//        Blob blob = bucket.create("nome.txt","Ricardo Ninja Lecheta".getBytes(), "text/html");
        File f = new File("Ferrari_FF.png");
        byte[] bytes = IOUtils.toByteArray(new FileInputStream(f));
        Blob blob = bucket.create(f.getName(),bytes, "image/png");

        // Deixa público
        Acl acl = blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        System.out.println(acl);

        // URL assinada
        URL signedUrl = blob.signUrl(365 * 10, TimeUnit.DAYS);

        System.out.println(signedUrl);

        System.out.println(blob.getMediaLink());

    }
}
