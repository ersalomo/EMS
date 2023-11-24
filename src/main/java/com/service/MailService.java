package com.service;

public interface MailService {

    void sendAsync(String email, String subject, String template);
}
