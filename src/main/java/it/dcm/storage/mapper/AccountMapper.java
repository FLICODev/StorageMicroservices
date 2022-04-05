package it.dcm.storage.mapper;

import com.google.firebase.auth.UserRecord;
import it.dcm.rest.authentication.FirebaseAccount;

public class AccountMapper {

    public static FirebaseAccount toAccount(UserRecord record){
        FirebaseAccount firebaseAccount = new FirebaseAccount();
        firebaseAccount.setEmail(record.getEmail());
        firebaseAccount.setUid(record.getUid());
        firebaseAccount.setLabel(record.getDisplayName());
        firebaseAccount.setEmailVerified(record.isEmailVerified());
        return firebaseAccount;
    }

}
