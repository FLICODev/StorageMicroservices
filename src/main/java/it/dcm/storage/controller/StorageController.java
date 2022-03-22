package it.dcm.storage.controller;


import it.dcm.rest.storage.RequestUpload;
import it.dcm.rest.storage.ResponseUpload;
import it.dcm.storage.command.UploadFileCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/storage")
public class StorageController {

    private final UploadFileCommand uploadFileCommand;

    public StorageController(UploadFileCommand uploadFileCommand) {
        this.uploadFileCommand = uploadFileCommand;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseUpload> uploadFile(@RequestBody RequestUpload request){
        return new ResponseEntity<>(this.uploadFileCommand.execute(request), HttpStatus.CREATED);
    }



}
