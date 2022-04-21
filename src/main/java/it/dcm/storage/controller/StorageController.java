package it.dcm.storage.controller;


import it.dcm.rest.storage.RequestUpload;
import it.dcm.rest.storage.ResponseUpload;
import it.dcm.storage.command.RemoveFileCommand;
import it.dcm.storage.command.UploadFileCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/storage")
public class StorageController {
    @Autowired
    private UploadFileCommand uploadFileCommand;
    @Autowired
    private RemoveFileCommand removeFileCommand;


    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseUpload> uploadFile(@RequestBody RequestUpload request){
        return new ResponseEntity<>(this.uploadFileCommand.execute(request), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<Void> remove(@PathVariable long id) {
        this.removeFileCommand.execute(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

}
