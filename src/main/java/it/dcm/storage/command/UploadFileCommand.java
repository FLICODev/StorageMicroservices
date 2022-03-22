package it.dcm.storage.command;

import it.dcm.rest.command.AbstractBaseCommand;
import it.dcm.rest.storage.RequestUpload;
import it.dcm.rest.storage.ResponseUpload;
import it.dcm.storage.mapper.FileCloudMapper;
import it.dcm.storage.model.FileCloud;
import it.dcm.storage.service.FileService;
import it.dcm.storage.service.StorageCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;


@Slf4j
@Component
@Scope("prototype")
public class UploadFileCommand  extends AbstractBaseCommand<RequestUpload, ResponseUpload> {


    private final FileService fileService;
    private final StorageCloud storageCloud;

    public UploadFileCommand(FileService fileService, StorageCloud storageCloud) {
        this.fileService = fileService;
        this.storageCloud = storageCloud;
    }

    @Override
    @Transactional
    public ResponseUpload execute(RequestUpload request) {
        log.info("Request upload file");
        log.info("Filename : {}", request.getFileName());
        log.info("Directory : {}", request.getDirectory());
        log.info("Content type : {}", request.getContentType());
        log.info("File size : {}", request.getSize());

        this.storageCloud.updateFile(request.getFile(), request.getFileName(), request.getDirectory(), request.getContentType());

        log.info("File uploaded");

        FileCloud find = new FileCloud();

        if (request.getId() != null){
            log.info("Search file with {} id", request.getId());
            try {
                find = this.fileService.get(request.getId());
                log.info("File found");
            } catch (ResponseStatusException ignored){
                log.info("File entity not found");
            }
        }

        FileCloudMapper.toEntity(find, request);

        this.fileService.merge(find);

        return FileCloudMapper.toResponse(find);
    }
}
