package it.dcm.storage.service.impl;

import it.dcm.rest.mail.EmailVerificationRequestDTO;
import it.dcm.storage.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${url.microservice.email.basePath}")
    private String basePath;


    @Override
    public void sendLinkEmailVerification(EmailVerificationRequestDTO dto) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<EmailVerificationRequestDTO> request = new HttpEntity<>(dto);
            restTemplate.exchange(basePath+"/verification", HttpMethod.POST, request, Void.class);
        } catch (Exception ex){
            log.error("Send email verification link not sended because : {}", ex.getLocalizedMessage());
        }
    }

    @Override
    public void sendLinkResetPassword(EmailVerificationRequestDTO dto) {
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<EmailVerificationRequestDTO> request = new HttpEntity<>(dto);
            restTemplate.exchange(basePath+"/reset-password", HttpMethod.POST, request, Void.class);
        } catch (Exception ex){
            log.error("Send email verification link not sended because : {}", ex.getLocalizedMessage());
        }
    }
}
