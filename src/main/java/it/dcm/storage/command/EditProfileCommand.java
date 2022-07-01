package it.dcm.storage.command;


import com.google.firebase.auth.UserRecord;
import it.dcm.rest.authentication.FirebaseAccount;
import it.dcm.rest.command.AbstractBaseCommand;
import it.dcm.rest.exception.ResponseEntityException;
import it.dcm.storage.dto.User;
import it.dcm.storage.mapper.AccountMapper;
import it.dcm.storage.security.SecurityService;
import it.dcm.storage.service.FirebaseAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static it.dcm.storage.exception.ExceptionEnum.AUTH_VALIDATION_TOKEN_NULL;

@Component
@Scope("prototype")
public class EditProfileCommand extends AbstractBaseCommand<Pair<String, String>, FirebaseAccount> {

    @Autowired
    private FirebaseAuthentication firebaseAuthentication;
    @Autowired
    private SecurityService securityService;


    @Override
    public FirebaseAccount execute(Pair<String, String> model) {
        String name = model.getFirst();
        String surname = model.getSecond();
        User user = securityService.getUser();

        if (user == null){
            throw new ResponseEntityException(AUTH_VALIDATION_TOKEN_NULL, AUTH_VALIDATION_TOKEN_NULL.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        UserRecord record = firebaseAuthentication.editDisplayName(name + " " + surname, user.getUid());

        return AccountMapper.toAccount(record);
    }
}
