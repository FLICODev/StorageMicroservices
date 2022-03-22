package it.dcm.storage.service;


public interface StorageCloud {

    String updateFile(byte[] file, String filename, String directory, String contentType);

}
