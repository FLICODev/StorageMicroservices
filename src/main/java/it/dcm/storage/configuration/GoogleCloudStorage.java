package it.dcm.storage.configuration;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
@Configuration
public class GoogleCloudStorage {

    @Value("${storage.config.key.url}")
    private String pathKeyFlicoGoogle;

    @PostConstruct
    public void initializeGoogleCloudStorage(){
        log.info("path file key {}", pathKeyFlicoGoogle);
        try {
            File file = new File(pathKeyFlicoGoogle);
            FileInputStream input = new FileInputStream(file);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(input))
                    .setDatabaseUrl("https://applicazioneflico.firebaseio.com")
                    .setStorageBucket("flico-307e8.appspot.com")
                    .build();
            log.info("Costruisco un firebase options");
            if(FirebaseApp.getApps().isEmpty()) {
                log.info("Applicazione vuota, inizializzo");
                FirebaseApp.initializeApp(options);
                log.info("Applicazione inizializzata");
                return;
            }
            log.info("inizializzo l'app ed è andato tutto ok");
        } catch (Exception e) {
            log.info("Si è verificato un error con firebase ");
            e.printStackTrace();
        }
    }

    public StorageClient getStorage() {
        return 	StorageClient.getInstance();
    }

}
