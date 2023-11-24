package com.service.impl;

import com.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.sender.name}")
    private String sender;
    @Value("${spring.mail.sender.mail}")
    private String mail;

    @Autowired
    @Qualifier("taskExecutor")
    private TaskExecutor taskExecutor;

    @Override
    public void sendAsync(String email, String subject, String template) {
        log.info("Email [][] {}", email);
        taskExecutor.execute(() -> send(email, subject, template));
    }

    public boolean send(String email, String subject, String message) {
        return send(null, email,subject,message);
    }

    private boolean send(String from,String email, String subject, String message) {
        MimeMessage mime = mailSender.createMimeMessage();
        if (StringUtils.isEmpty(from)) {
            from = sender;
        }
        if (StringUtils.isEmpty(from)) {
            from = "admin@mail.com";
        }
        boolean success = false;
        try {
            log.info("Sending email to: "+ "ersalomo2002@gmail.com");
            log.info("Sending email from: "+from);
            log.info("Sending email with subject: "+subject);

            MimeMessageHelper helper = new MimeMessageHelper(mime, true);
            helper.setFrom(from,subject);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mime);
            success = true;
        } catch (Exception e) {
            log.error("[Email error:] "+e.getMessage());
        }

        return success;
    }

}
