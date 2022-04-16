package it.dcm.storage.dto;

import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Credentials {

    private FirebaseToken decodedToken;
    private String uid;
    private Date date;

}