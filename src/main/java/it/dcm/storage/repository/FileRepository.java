package it.dcm.storage.repository;

import it.dcm.storage.model.FileCloud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileCloud, Long> {



}
