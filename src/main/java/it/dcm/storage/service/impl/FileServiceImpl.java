package it.dcm.storage.service.impl;

import it.dcm.storage.model.FileCloud;
import it.dcm.storage.repository.FileRepository;
import it.dcm.storage.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Override
    public FileCloud merge(FileCloud file) {
        return this.fileRepository.saveAndFlush(file);
    }

    @Override
    public FileCloud get(long id) {
        return this.fileRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
