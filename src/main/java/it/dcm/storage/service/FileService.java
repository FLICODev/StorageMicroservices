package it.dcm.storage.service;

import it.dcm.storage.model.FileCloud;

public interface FileService {

    FileCloud merge(FileCloud file);

    FileCloud get(long id);

}
