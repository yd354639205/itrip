package cn.itrip.auth.service;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public interface MailService {

    public void sendAccountActivateEmail(String code, String activationCode) throws Exception;

    public MimeMessage getMimeMessage(Session session, String code, String activationCode) throws Exception;

}
