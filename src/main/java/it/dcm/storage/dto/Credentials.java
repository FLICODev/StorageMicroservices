package it.dcm.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Credentials {

    private String token;
    private String uid;
    private Date date;

}