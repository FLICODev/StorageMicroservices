package it.dcm.storage.service;

import it.dcm.rest.mail.EmailVerificationRequestDTO;

public interface EmailService {


    void sendLinkEmailVerification(EmailVerificationRequestDTO request);

    void sendLinkResetPassword(EmailVerificationRequestDTO request);

}
