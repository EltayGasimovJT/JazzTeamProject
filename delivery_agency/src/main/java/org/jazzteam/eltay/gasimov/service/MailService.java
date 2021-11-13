package org.jazzteam.eltay.gasimov.service;

import javax.mail.internet.MimeMessage;

public interface MailService {
    void sendEmail(MimeMessage preparator);
}
