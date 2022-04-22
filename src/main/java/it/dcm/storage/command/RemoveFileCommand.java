package it.dcm.storage.command;

import it.dcm.rest.command.AbstractBaseCommand;
import it.dcm.storage.model.FileCloud;
import it.dcm.storage.security.SecurityService;
import it.dcm.storage.service.FileService;
import it.dcm.storage.service.StorageCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;


@Slf4j
@Component
@Scope("prototype")
public class RemoveFileCommand extends AbstractBaseCommand<Long, Void> {

    @Autowired
    private FileService fileService;
    @Autowired
    private StorageCloud storageCloud;
    @Autowired
    private SecurityService securityService;

    @Override
    @Transactional
    public Void execute(Long model) {

        FileCloud fileCloud = fileService.get(model);
        log.info("File found into DB {}", fileCloud.getFilename());

        storageCloud.deleteFile(fileCloud.getDirectory()+"/"+fileCloud.getFilename());

        fileCloud.setArchived(LocalDateTime.now());
        fileCloud.setArchiver(securityService.getUser().getUid());

        fileService.merge(fileCloud);

        return null;
    }
}
