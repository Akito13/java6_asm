package sof3021.ca4.nhom1.asm.qls.service;

import jakarta.mail.MessagingException;
import sof3021.ca4.nhom1.asm.qls.model.MailInfo;

public interface Mailable {
    void send(MailInfo mail) throws MessagingException;
    void send(String to, String subject, String body) throws MessagingException;
}
