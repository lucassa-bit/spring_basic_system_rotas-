package com.AEDsistema.relatorioMVC.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.messaging.FirebaseMessaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;

@Configuration
public class FirebaseConfig {

    @Value(value = "${app.firebase-config-file}")
    private Resource serviceAccountResource;

    @Bean
    public FirebaseApp createFireBaseApp() throws IOException {
        InputStream serviceAccount = new FileInputStream(
                new File("src/main/resources/firebase-config/serviceAccount.json"));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                // .setDatabaseUrl("<db-url>")
                .setStorageBucket("projeto-pe-express-rota-347910.appspot.com")
                .build();

        return FirebaseApp.getApps().isEmpty() ? FirebaseApp.initializeApp(options) : FirebaseApp.getApps().get(0);
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public StorageClient createFirebaseStorage() {
        return StorageClient.getInstance();
    }

    @Bean
    @DependsOn(value = "createFireBaseApp")
    public FirebaseMessaging createFirebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}
