package it.dcm.storage.command;

import com.google.firebase.auth.UserRecord;
import it.dcm.rest.command.AbstractBaseCommand;
import it.dcm.rest.mail.EmailVerificationRequestDTO;
import it.dcm.storage.service.EmailService;
import it.dcm.storage.service.FirebaseAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ResetPasswordCommand extends AbstractBaseCommand<String, Void> {

    @Autowired
    private FirebaseAuthentication firebaseAuthentication;
    @Autowired
    private EmailService emailService;

    @Override
    public Void execute(String email) {

        UserRecord user = this.firebaseAuthentication.getFromMail(email);
        String link = this.firebaseAuthentication.getLinkResetPassword(email, user.getUid());

        EmailVerificationRequestDTO requestVerification = new EmailVerificationRequestDTO();
        requestVerification.setEmail(user.getEmail());
        requestVerification.setLabel(user.getDisplayName());
        requestVerification.setLink(link);

        this.emailService.sendLinkResetPassword(requestVerification);

        return null;
    }
}
