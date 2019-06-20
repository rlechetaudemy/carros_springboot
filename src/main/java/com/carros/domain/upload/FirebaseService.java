package com.carros.domain.upload;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//https://firebase.google.com/docs/storage/admin/start
class FirebaseService {
    public static void main(String[] args) throws IOException {
        InputStream in =
                FirebaseService.class.getResourceAsStream("/serviceAccountKey.json");

        System.out.println(in);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(in))
                .setStorageBucket("carros-3a13e.appspot.com")
                .setDatabaseUrl("https://carros-3a13e.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        Bucket bucket = StorageClient.getInstance().bucket();
        System.out.println(bucket);

//        Blob blob = bucket.create("nome.txt","Ricardo Ninja Lecheta".getBytes(), "text/html");

        File f = new File("Ferrari_FF.png");

        byte[] bytes = IOUtils.toByteArray(new FileInputStream(f));

        Blob blob = bucket.create(f.getName(),bytes, "image/png");

        URL signedUrl = blob.signUrl(365 * 10, TimeUnit.DAYS);

        Acl acl = blob.createAcl(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER));
System.out.println(acl);

        System.out.println(signedUrl);


//        Storage storage = StorageOptions.getDefaultInstance().getService();
//        BlobId blobId = BlobId.of(bucket.getName(), "blob_name");
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
//        Blob blob2 = storage.create(blobInfo, "Hello, Cloud Storage!".getBytes("UTF-8"));

//        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//
//        System.out.println(new File(".").getAbsoluteFile());
//        System.out.println(blob);
//        System.out.println(blob.getMediaLink());

    }
}

/**
 service firebase.storage {
 match /b/{bucket}/o {
 match /{allPaths=**} {
 allow read, write: if request.auth != null;
 }
 }
 }
*/