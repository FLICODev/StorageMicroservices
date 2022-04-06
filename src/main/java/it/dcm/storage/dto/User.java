package it.dcm.storage.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String uid;
    private String email;
    private String label;
    private boolean emailVerified;
}
