package it.dcm.storage.mapper;

import it.dcm.rest.storage.RequestUpload;
import it.dcm.rest.storage.ResponseUpload;
import it.dcm.storage.model.FileCloud;


public class FileCloudMapper {


    public static void toEntity(FileCloud file, RequestUpload requestUpload){
        file.setFilename(requestUpload.getFileName());
        file.setContentType(requestUpload.getContentType());
        file.setDirectory(requestUpload.getDirectory());
        file.setSize(requestUpload.getSize());
        if (file.getId() == 0){
            file.setCreator("Admin");
        } else {
            file.setUpdater((file.getUpdater() == null) ? "Admin" : file.getUpdater());
        }
    }


    public static ResponseUpload toResponse(FileCloud fileCloud){
        ResponseUpload responseUpload = new ResponseUpload();
        responseUpload.setId(fileCloud.getId());
        responseUpload.setDirectoryComplete(fileCloud.getDirectory() + "/" + fileCloud.getFilename());
        return responseUpload;
    }

}

