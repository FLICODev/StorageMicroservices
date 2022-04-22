package it.dcm.storage.service.impl;


import com.google.cloud.storage.*;
import it.dcm.storage.configuration.GoogleCloudStorage;
import it.dcm.storage.service.StorageCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
public class StorageCloudImpl implements StorageCloud {

    private final GoogleCloudStorage gcs;

    @Value("${storage.config.bucket}")
    private String bucketName;

    public StorageCloudImpl(GoogleCloudStorage gcs) {
        this.gcs = gcs;
    }

    @Override
    public void updateFile(byte[] file, String filename, String directory, String contentType) {
        log.info("UPLOAD FILE ");
        log.info("Name file : {}", filename);
        log.info("Directory : {}", directory);
        try {
            Bucket bucket = gcs.getStorage().bucket();
            Storage storage = bucket.getStorage();
            BlobId blobId = BlobId.of(this.bucketName ,  directory + "/" + filename);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file).toBuilder().setContentType(contentType)
                    .setCacheControl("max-age=0")
                    .build().update();
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        } catch (Exception exception) {
            log.error("Error with upload image {}", exception.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File not uploaded");
        }
    }

    @Override
    public void deleteFile(String path) {
        try {
            Bucket bucket = gcs.getStorage().bucket();
            Storage storage = bucket.getStorage();
            BlobId blobId = BlobId.of(this.bucketName, path);
            storage.delete(blobId);
        } catch (Exception exception) {
            log.error("Error with deleted image {}", exception.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File not deleted");
        }
    }


}
