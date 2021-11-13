package org.jazzteam.eltay.gasimov.service.impl;

import freemarker.template.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jazzteam.eltay.gasimov.entity.ClientsCode;
import org.jazzteam.eltay.gasimov.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger logger = Logger.getLogger(MailServiceImpl.class);
    private static final String CODE_MAIL_LETTER_TEMPLATE = "code_email.html";
    private static final String EMAIL_FROM = "romanov1234567890987@mail.ru";

    @Autowired
    JavaMailSender mailSender;
    @Qualifier("freeMarkerConfiguration")
    @Autowired
    Configuration freemarkerConfiguration;

    @Override
    public void sendEmail(MimeMessage preparator) {
        try {
            mailSender.send(preparator);
        } catch (MailException ex) {
            logger.error(ex.getMessage());
        }
    }

    public void sendCodeMail(ClientsCode code) {
        MimeMessage preparator = getClientsMailPreparator(code);
        sendEmail(preparator);
    }

    private MimeMessage getClientsMailPreparator(ClientsCode code) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            helper.setSubject(String.format("GASDEL delivery agency: код подтверждения для пользователя %s", code.getClient().getName()));
            helper.setFrom(EMAIL_FROM);
            if (StringUtils.isNotEmpty(code.getClient().getEmail())) {
                helper.setTo(new InternetAddress(code.getClient().getEmail()));
            } else {
                throw new InvalidParameterException("Неправильное письмо");
            }
            String text = getClientMailMessageTemplate(code);
            logger.info("Template content : " + text);
            helper.setText(text, true);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException("Ошбика парсинга письма, отправка отменена");
        }
        return mimeMessage;
    }

    private String getClientMailMessageTemplate(ClientsCode code) {
        Map<String, Object> model = new HashMap<>();
        model.put("name", code.getClient().getName());
        model.put("code", code.getGeneratedCode());
        return getEmailTemplateContent(model, CODE_MAIL_LETTER_TEMPLATE);
    }

    private String getEmailTemplateContent(Map<String, Object> model, String templateName)  {
        StringBuilder content = new StringBuilder();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate(templateName), model));
            return content.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new IllegalStateException("Ошибка парсинга письма");
        }
    }
}
