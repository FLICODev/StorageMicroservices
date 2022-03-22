package it.dcm.storage.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "file")
public class FileCloud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;
    @Column(name = "creator", nullable = false, updatable = false)
    private String creator;

    @Column(name = "updated")
    private LocalDateTime updated;
    @Column(name = "updater")
    private String updater;

    @Column(name = "archived")
    private LocalDateTime archived;
    @Column(name = "archiver")
    private String archiver;


    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "directory", nullable = false)
    private String directory;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @PrePersist
    public void prePersist(){
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updated = LocalDateTime.now();
    }


}
