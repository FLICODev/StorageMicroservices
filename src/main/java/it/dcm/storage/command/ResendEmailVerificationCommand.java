package it.dcm.storage.command;

import com.google.firebase.auth.UserRecord;
import it.dcm.rest.command.AbstractBaseCommand;
import it.dcm.rest.mail.EmailVerificationRequestDTO;
import it.dcm.storage.service.EmailService;
import it.dcm.storage.service.FirebaseAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class ResendEmailVerificationCommand extends AbstractBaseCommand<String, Void> {


    @Autowired
    private FirebaseAuthentication firebaseAuthentication;
    @Autowired
    private EmailService emailService;



    @Override
    public Void execute(String email) {
        UserRecord record = firebaseAuthentication.getFromMail(email);
        EmailVerificationRequestDTO requestVerification = new EmailVerificationRequestDTO();
        requestVerification.setEmail(record.getEmail());
        requestVerification.setLabel(record.getDisplayName());
        requestVerification.setLink(firebaseAuthentication.getLinkConfirmEmail(record.getEmail()));
        log.info("Email request object is {} ", requestVerification.getEmail());
        emailService.sendLinkEmailVerification(requestVerification);

        return null;
    }
}
