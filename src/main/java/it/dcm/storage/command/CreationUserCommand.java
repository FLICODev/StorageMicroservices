package it.dcm.storage.command;

import com.google.firebase.auth.UserRecord;
import it.dcm.rest.authentication.FirebaseAccount;
import it.dcm.rest.command.AbstractBaseCommand;
import it.dcm.rest.mail.EmailVerificationRequestDTO;
import it.dcm.storage.mapper.AccountMapper;
import it.dcm.storage.service.EmailService;
import it.dcm.storage.service.FirebaseAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CreationUserCommand extends AbstractBaseCommand<FirebaseAccount, FirebaseAccount> {

    @Autowired
    private FirebaseAuthentication firebaseAuthentication;
    @Autowired
    private EmailService emailService;

    @Override
    public FirebaseAccount execute(FirebaseAccount model) {

        UserRecord record = this.firebaseAuthentication.create(model.getEmail(), model.getPassword(), model.getLabel());
        String linkEmail = this.firebaseAuthentication.getLinkConfirmEmail(record.getEmail(), record.getUid());

        EmailVerificationRequestDTO requestVerification = new EmailVerificationRequestDTO();
        requestVerification.setEmail(record.getEmail());
        requestVerification.setLabel(record.getDisplayName());
        requestVerification.setLink(linkEmail);

        emailService.sendLinkEmailVerification(requestVerification);

        return AccountMapper.toAccount(record);
    }
}
