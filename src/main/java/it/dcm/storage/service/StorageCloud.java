package it.dcm.storage.service;


public interface StorageCloud {

    void updateFile(byte[] file, String filename, String directory, String contentType);

    void deleteFile(String path);

}
