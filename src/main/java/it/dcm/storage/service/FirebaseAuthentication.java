package it.dcm.storage.service;

import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

public interface FirebaseAuthentication {

    FirebaseToken validateTokenId(String bearerToken);

    UserRecord create(String email, String password, String label);

    UserRecord getFromMail(String email);

    String getLinkConfirmEmail(String email);

    String getLinkResetPassword(String email, String uid);

    UserRecord editDisplayName(String displayName, String uid);

}
