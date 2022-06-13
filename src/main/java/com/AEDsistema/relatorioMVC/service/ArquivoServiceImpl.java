package com.AEDsistema.relatorioMVC.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.springframework.stereotype.Service;

@Service
public class ArquivoServiceImpl implements ArquivoService{
    private void uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("-------- FIREBASE STORAGE URL --------", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(new File("src/main/resources/firebase-config/serviceAccount.json")));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public Object upload(File file, String rotaId) {
        try {
            String fileName = file.getName(); // to get original file name
            fileName = "rota-" + rotaId + getExtension(fileName);
            this.uploadFile(file, fileName); // to get uploaded file link
            file.delete(); // to delete the copy of uploaded file stored in the project folder
            return "Okay";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

    }

    public Object download(String rotaId) throws IOException {
        String destFileName = rotaId;
        String destFilePath = destFileName; // to set destination file path

        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("src/main/resources/firebase-config/serviceAccount.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of("-------- FIREBASE STORAGE URL --------", rotaId));
        blob.downloadTo(Paths.get(destFilePath));
        return "Okay";
    }
}
